package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.*;
import cn.wegfan.forum.model.entity.*;
import cn.wegfan.forum.model.vo.request.*;
import cn.wegfan.forum.model.vo.response.*;
import cn.wegfan.forum.util.BusinessException;
import cn.wegfan.forum.util.HtmlUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TopicServiceFacade {

    @Autowired
    private TopicService topicService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private UserServiceFacade userServiceFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TopicOperationLogService topicOperationLogService;

    @Autowired
    private ReplyService replyService;

    public IdResponseVo addTopic(AddTopicRequestVo requestVo) {
        Long boardId = requestVo.getBoardId();
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(boardId);
        if (permission.getBanCreateTopic() ||
                !requestVo.getAttachments().isEmpty() && permission.getBanUploadAttachment()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        TopicTypeEnum topicTypeEnum = TopicTypeEnum.fromValue(requestVo.getType());
        if (topicTypeEnum.equals(TopicTypeEnum.ANNOUNCEMENT) && !permission.getBoardAdmin()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        Topic topic = mapperFacade.map(requestVo, Topic.class);
        topicService.addTopic(topic);
        userService.increaseUserTopicCountByUserId(userId);

        Set<Long> attachmentIdList = requestVo.getAttachments().stream()
                .map(AttachmentRequestVo::getId)
                .collect(Collectors.toSet());

        Map<Long, Attachment> attachmentMap = attachmentService.listNotDeletedAttachmentsByAttachmentIdList(attachmentIdList)
                .stream()
                .collect(Collectors.toMap(Attachment::getId, Function.identity()));

        // 过滤不符合要求的附件
        List<AttachmentRequestVo> filteredAttachmentRequestVoList = requestVo.getAttachments().stream()
                .filter(item -> {
                    // 只有上传者为当前登录用户，且不属于任何主题的附件才符合要求
                    Attachment attachment = attachmentMap.get(item.getId());
                    return attachment.getUploaderUserId().equals(userId) &&
                            attachment.getTopicId() == null &&
                            attachment.getBoardId() == null;
                })
                .collect(Collectors.toList());

        log.debug("request: {}, filtered: {}", requestVo.getAttachments(), filteredAttachmentRequestVoList);

        Map<Long, String> attachmentIdDescriptionMap = filteredAttachmentRequestVoList.stream()
                .collect(Collectors.toMap(AttachmentRequestVo::getId, AttachmentRequestVo::getDescription));

        Set<Long> filteredAttachmentIdList = filteredAttachmentRequestVoList.stream()
                .map(AttachmentRequestVo::getId)
                .collect(Collectors.toSet());

        attachmentService.batchUpdateAttachmentDescription(attachmentIdDescriptionMap);
        attachmentService.batchAddAttachmentTopicAndBoardIdByAttachmentIdList(topic.getId(), boardId,
                filteredAttachmentIdList);

        return new IdResponseVo(topic.getId());
    }

    public void updateTopic(UpdateTopicRequestVo requestVo) {
        Topic topic = topicService.getNotDeletedTopicByTopicId(requestVo.getTopicId());
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        if (topic == null) {
            throw new BusinessException(BusinessErrorEnum.TOPIC_NOT_FOUND);
        }
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(topic.getBoardId());
        if (!permission.getBoardAdmin() && !topic.getSubmitterUserId().equals(userId)) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        if (!permission.getBoardAdmin() && !topic.getType().equals(requestVo.getType())) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        mapperFacade.map(requestVo, topic);

        topicService.updateTopic(topic);

        Set<Long> requestAttachmentIdList = requestVo.getAttachments().stream()
                .map(AttachmentRequestVo::getId)
                .collect(Collectors.toSet());

        Map<Long, Attachment> requestAttachmentMap = attachmentService.listNotDeletedAttachmentsByAttachmentIdList(requestAttachmentIdList)
                .stream()
                .collect(Collectors.toMap(Attachment::getId, Function.identity()));

        // 过滤不符合要求的附件
        List<AttachmentRequestVo> filteredAttachmentRequestVoList = requestVo.getAttachments().stream()
                .filter(item -> {
                    // 只有上传者为当前登录用户且不属于任何主题的附件，或已上传且所属主题为当前主题的才符合要求
                    Attachment attachment = requestAttachmentMap.get(item.getId());
                    return attachment.getUploaderUserId().equals(userId) &&
                            attachment.getTopicId() == null &&
                            attachment.getBoardId() == null ||
                            attachment.getTopicId().equals(topic.getId());
                })
                .collect(Collectors.toList());

        // 重新赋值过滤后的编号列表
        requestAttachmentIdList = filteredAttachmentRequestVoList.stream()
                .map(AttachmentRequestVo::getId)
                .collect(Collectors.toSet());

        log.debug("request: {}, filtered: {}", requestVo.getAttachments(), filteredAttachmentRequestVoList);

        List<Attachment> currentAttachmentList = attachmentService.listNotDeletedAttachmentsByTopicId(topic.getId());
        Set<Long> currentAttachmentIdList = currentAttachmentList.stream()
                .map(Attachment::getId)
                .collect(Collectors.toSet());

        Set<Long> needToDelete = new HashSet<Long>(CollectionUtils.removeAll(currentAttachmentIdList,
                requestAttachmentIdList));
        Set<Long> needToAdd = new HashSet<Long>(CollectionUtils.removeAll(requestAttachmentIdList,
                currentAttachmentIdList));

        Map<Long, String> attachmentIdDescriptionMap = filteredAttachmentRequestVoList.stream()
                .collect(Collectors.toMap(AttachmentRequestVo::getId, AttachmentRequestVo::getDescription));

        attachmentService.batchDeleteAttachmentsByAttachmentIdList(needToDelete);
        attachmentService.batchUpdateAttachmentDescription(attachmentIdDescriptionMap);
        attachmentService.batchAddAttachmentTopicAndBoardIdByAttachmentIdList(topic.getId(), topic.getBoardId(),
                needToAdd);
    }

    public PageResultVo<TopicResponseVo> getBoardTopicList(Long boardId, TopicListTypeEnum topicTypeEnum, TopicListSortEnum topicSortEnum,
                                                           long pageIndex, long pageSize) {
        Board board = boardService.getNotDeletedBoardByBoardId(boardId);
        if (board == null) {
            throw new BusinessException(BusinessErrorEnum.BOARD_NOT_FOUND);
        }
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(boardId);
        if (permission.getBanVisit()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Topic> pageResult = topicService.listNotDeletedTopicsByPageAndBoardId(page, boardId,
                topicTypeEnum, topicSortEnum);

        List<TopicResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), TopicResponseVo.class);
        responseVoList.forEach(item -> {
            List<String> imageList = HtmlUtil.getImageSourceList(item.getContent())
                    .stream()
                    .limit(Constant.TOPIC_LIST_MAX_IMAGE_COUNT)
                    .collect(Collectors.toList());
            item.setImages(imageList);

            User submitter = userService.getNotDeletedUserByUserId(item.getSubmitterUserId());
            item.setSubmitterAvatarPath(submitter.getAvatarPath());
            item.setSubmitterNickname(submitter.getNickname());

            User lastReplier = userService.getUserByUserId(item.getLastReplierUserId());
            item.setLastReplierNickname(lastReplier.getNickname());

            item.setShortContent(StringUtils.substring(item.getContentText(), 0, 500));
        });
        return new PageResultVo<>(responseVoList, pageResult);
    }

    public PageResultVo<TopicResponseVo> getUserTopicList(Long userId, TopicListTypeEnum topicTypeEnum, TopicListSortEnum topicSortEnum,
                                                          long pageIndex, long pageSize) {
        User user = userService.getNotDeletedUserByUserId(userId);
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Topic> pageResult = topicService.listNotDeletedTopicsByPageAndSubmitterUserId(page, userId,
                topicTypeEnum, topicSortEnum);

        List<TopicResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), TopicResponseVo.class);
        responseVoList.forEach(item -> {
            List<String> imageList = HtmlUtil.getImageSourceList(item.getContent())
                    .stream()
                    .limit(Constant.TOPIC_LIST_MAX_IMAGE_COUNT)
                    .collect(Collectors.toList());
            item.setImages(imageList);

            User submitter = userService.getNotDeletedUserByUserId(item.getSubmitterUserId());
            item.setSubmitterAvatarPath(submitter.getAvatarPath());
            item.setSubmitterNickname(submitter.getNickname());

            User lastReplier = userService.getUserByUserId(item.getLastReplierUserId());
            item.setLastReplierNickname(lastReplier.getNickname());

            item.setShortContent(StringUtils.substring(item.getContentText(), 0, 500));
        });
        return new PageResultVo<>(responseVoList, pageResult);
    }

    public PageResultVo<TopicAdminDetailResponseVo> getTopicList(Long boardId, String username, TopicListTypeEnum topicTypeEnum,
                                                                 String keyword, Date startTime, Date endTime,
                                                                 TopicListSortEnum topicSortEnum, long pageIndex, long pageSize) {
        Long userId = null;
        if (!StringUtils.isBlank(username)) {
            User user = userService.getNotDeletedUserByUsername(username.trim());
            if (user != null) {
                userId = user.getId();
            } else {
                userId = -1L;
            }
        }
        Long currentLoginUserId = (Long)SecurityUtils.getSubject().getPrincipal();
        List<Long> allAdminBoardIdList = boardService.listNotDeletedAllAdminBoardIdsByUserId(currentLoginUserId);

        if (boardId != null && !allAdminBoardIdList.contains(boardId)) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }
        List<Long> boardIdList = boardId == null ? allAdminBoardIdList : Collections.singletonList(boardId);

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Topic> pageResult = topicService.listNotDeletedTopicsByPage(page, boardIdList, userId,
                topicTypeEnum, keyword, startTime, endTime, topicSortEnum);

        List<TopicAdminDetailResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), TopicAdminDetailResponseVo.class);
        responseVoList.forEach(item -> {
            User submitter = userService.getNotDeletedUserByUserId(item.getSubmitterUserId());
            item.setSubmitterUsername(submitter.getUsername());
            item.setSubmitterNickname(submitter.getNickname());
            item.setSubmitterAvatarPath(submitter.getAvatarPath());

            item.setShortContent(StringUtils.substring(item.getContentText(), 0, 500));

            if (item.getEditorUserId() != null) {
                User editor = userService.getUserByUserId(item.getEditorUserId());
                item.setEditorUsername(editor.getUsername());
                item.setEditorNickname(editor.getNickname());
            }

            Board board = boardService.getNotDeletedBoardByBoardId(item.getBoardId());
            item.setBoardName(board.getName());

            Category category = categoryService.getNotDeletedCategoryByCategoryId(item.getCategoryId());
            item.setCategoryName(category.getName());
        });
        return new PageResultVo<>(responseVoList, pageResult);
    }

    public TopicDetailResponseVo getTopicDetail(Long topicId) {
        Topic topic = topicService.getNotDeletedTopicByTopicId(topicId);
        if (topic == null) {
            throw new BusinessException(BusinessErrorEnum.TOPIC_NOT_FOUND);
        }
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(topic.getBoardId());
        if (permission.getBanVisit()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        TopicDetailResponseVo responseVo = mapperFacade.map(topic, TopicDetailResponseVo.class);

        User submitter = userService.getNotDeletedUserByUserId(topic.getSubmitterUserId());
        responseVo.setSubmitterUsername(submitter.getUsername());
        responseVo.setSubmitterNickname(submitter.getNickname());
        responseVo.setSubmitterAvatarPath(submitter.getAvatarPath());

        User lastReplier = userService.getUserByUserId(topic.getLastReplierUserId());
        responseVo.setLastReplierUsername(lastReplier.getUsername());
        responseVo.setLastReplierNickname(lastReplier.getNickname());

        Board board = boardService.getNotDeletedBoardByBoardId(topic.getBoardId());
        responseVo.setBoardName(board.getName());

        Category category = categoryService.getNotDeletedCategoryByCategoryId(topic.getCategoryId());
        responseVo.setCategoryName(category.getName());

        if (responseVo.getEditorUserId() != null) {
            User editor = userService.getUserByUserId(responseVo.getEditorUserId());
            responseVo.setEditorUsername(editor.getUsername());
            responseVo.setEditorNickname(editor.getNickname());
        }

        List<Attachment> attachmentList = attachmentService.listNotDeletedAttachmentsByTopicId(topicId);
        List<TopicAttachmentResponseVo> attachmentResponseVoList = mapperFacade.mapAsList(attachmentList,
                TopicAttachmentResponseVo.class);
        responseVo.setAttachments(attachmentResponseVoList);

        if (!permission.getBoardAdmin()) {
            responseVo.setSubmitterIp(null);
            responseVo.setLastReplierIp(null);
            responseVo.setEditorIp(null);
        }

        topicService.increaseTopicViewCountByTopicId(topicId);
        return responseVo;
    }

    public void manageTopic(ManageTopicRequestVo requestVo) {
        Long currentUserId = (Long)SecurityUtils.getSubject().getPrincipal();
        ManageTopicActionEnum actionEnum = ManageTopicActionEnum.fromValue(requestVo.getAction());

        Set<Long> currentUserAdminBoardIdList = new HashSet<>(boardService.listNotDeletedAllAdminBoardIdsByUserId(currentUserId));

        List<Long> filteredIdList = requestVo.getIdList()
                .stream()
                .filter(id -> {
                    Topic topic = topicService.getNotDeletedTopicByTopicId(id);
                    return topic != null && currentUserAdminBoardIdList.contains(topic.getBoardId());
                })
                .collect(Collectors.toList());

        topicService.batchManageTopicByTopicIdsAndAction(filteredIdList, actionEnum);
        topicOperationLogService.batchAddTopicOperationLog(filteredIdList, actionEnum, requestVo.getReason());

        if (actionEnum == ManageTopicActionEnum.DELETE) {
            topicService.batchDeleteTopicByTopicIds(filteredIdList);
            attachmentService.batchDeleteAttachmentByTopicIds(filteredIdList);
            filteredIdList.forEach(topicId -> {
                replyService.batchCascadeDeleteReply(null, topicId, null, null);
            });
        }
    }

    public void deleteTopic(IdRequestVo requestVo) {
        Long currentUserId = (Long)SecurityUtils.getSubject().getPrincipal();
        Long topicId = requestVo.getId();
        Topic topic = topicService.getNotDeletedTopicByTopicId(topicId);
        if (topic == null) {
            throw new BusinessException(BusinessErrorEnum.TOPIC_NOT_FOUND);
        }

        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(topic.getBoardId());
        if (!permission.getBoardAdmin() && !topic.getSubmitterUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        topicService.batchDeleteTopicByTopicIds(Collections.singletonList(topicId));
        replyService.batchCascadeDeleteReply(null, topicId, null, null);
        attachmentService.batchDeleteAttachmentByTopicIds(Collections.singletonList(topicId));
    }

    public List<TopicOperationLogResponseVo> getTopicOperationLog(Long topicId) {
        Topic topic = topicService.getNotDeletedTopicByTopicId(topicId);
        if (topic == null) {
            throw new BusinessException(BusinessErrorEnum.TOPIC_NOT_FOUND);
        }

        List<TopicOperationLog> topicOperationLogList = topicOperationLogService.listTopicOperationLogsByTopicId(topicId);
        List<TopicOperationLogResponseVo> responseVoList = mapperFacade.mapAsList(topicOperationLogList, TopicOperationLogResponseVo.class);
        responseVoList.forEach(item -> {
            User operator = userService.getUserByUserId(item.getOperatorUserId());
            item.setOperatorUsername(operator.getUsername() +
                    (operator.getDeleteTime() == null ? "" : " （已删除）"));
            item.setOperateType(ManageTopicActionEnum.fromValue(item.getOperateType()).getName());
        });
        return responseVoList;
    }

}
