package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.constant.TopicReplyListSortEnum;
import cn.wegfan.forum.model.entity.Reply;
import cn.wegfan.forum.model.entity.Topic;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.AddReplyRequestVo;
import cn.wegfan.forum.model.vo.request.IdListRequestVo;
import cn.wegfan.forum.model.vo.request.IdRequestVo;
import cn.wegfan.forum.model.vo.request.UpdateReplyRequestVo;
import cn.wegfan.forum.model.vo.response.*;
import cn.wegfan.forum.util.BusinessException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ReplyServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserServiceFacade userServiceFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    public void addReply(AddReplyRequestVo requestVo) {
        Topic topic = topicService.getNotDeletedTopicByTopicId(requestVo.getTopicId());
        if (topic == null) {
            throw new BusinessException(BusinessErrorEnum.TOPIC_NOT_FOUND);
        }
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        if (userServiceFacade.getUserBoardPermission(topic.getBoardId()).getBanReply()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        Reply reply = mapperFacade.map(requestVo, Reply.class);
        replyService.addReply(reply);
        topicService.updateTopicLastReplyByTopicId(topic.getId());
        userService.increaseUserReplyCountByUserId(userId);
        topicService.increaseTopicReplyCountByTopicId(topic.getId());
    }

    public void updateReply(UpdateReplyRequestVo requestVo) {
        Reply reply = replyService.getReplyByReplyId(requestVo.getReplyId());
        if (reply == null) {
            throw new BusinessException(BusinessErrorEnum.REPLY_NOT_FOUND);
        }
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(reply.getBoardId());
        if (!permission.getBoardAdmin() && !reply.getReplierUserId().equals(userId)) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        mapperFacade.map(requestVo, reply);
        replyService.updateReply(reply);
    }

    public void batchDeleteReply(IdListRequestVo requestVo) {
        Long currentUserId = (Long)SecurityUtils.getSubject().getPrincipal();
        Set<Long> currentUserAdminBoardIdList = new HashSet<>(boardService.listNotDeletedAllAdminBoardIdsByUserId(currentUserId));

        List<Long> filteredIdList = requestVo.getIdList()
                .stream()
                .filter(id -> {
                    Reply reply = replyService.getReplyByReplyId(id);
                    return reply != null && currentUserAdminBoardIdList.contains(reply.getBoardId());
                })
                .collect(Collectors.toList());

        replyService.batchDeleteReplyByReplyIdList(filteredIdList);
    }

    public void deleteReply(IdRequestVo requestVo) {
        Reply reply = replyService.getReplyByReplyId(requestVo.getId());
        if (reply == null) {
            throw new BusinessException(BusinessErrorEnum.REPLY_NOT_FOUND);
        }
        Long currentUserId = (Long)SecurityUtils.getSubject().getPrincipal();
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(reply.getBoardId());
        if (!permission.getBoardAdmin() && !reply.getReplierUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }
        replyService.batchDeleteReplyByReplyIdList(Collections.singletonList(requestVo.getId()));
    }

    public PageResultVo<TopicReplyResponseVo> getTopicReplyList(Long topicId, Boolean submitterOnly, TopicReplyListSortEnum sortEnum,
                                                                long pageIndex, long pageSize) {
        Topic topic = topicService.getNotDeletedTopicByTopicId(topicId);
        if (topic == null) {
            throw new BusinessException(BusinessErrorEnum.TOPIC_NOT_FOUND);
        }

        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(topic.getBoardId());
        if (permission.getBanVisit()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        Long replierUserId = null;
        if (submitterOnly != null && submitterOnly) {
            replierUserId = topic.getSubmitterUserId();
        }

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Reply> pageResult = replyService.listNotDeletedRepliesByPageAndTopicId(page, topicId,
                replierUserId, sortEnum);
        List<TopicReplyResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), TopicReplyResponseVo.class);
        responseVoList.forEach(item -> {
            item.setTopicTitle(topic.getTitle());

            User replier = userService.getUserByUserId(item.getReplierUserId());
            item.setReplierNickname(replier.getNickname());
            item.setReplierAvatarPath(replier.getAvatarPath());

            User editor = userService.getUserByUserId(item.getEditorUserId());
            if (editor != null) {
                item.setEditorNickname(editor.getNickname());
            }
        });
        return new PageResultVo<>(responseVoList, pageResult);
    }

    public PageResultVo<UserReplyResponseVo> getUserReplyList(Long userId, long pageIndex, long pageSize) {
        User user = userService.getNotDeletedUserByUserId(userId);
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Reply> pageResult = replyService.listNotDeletedRepliesByPageAndReplierUserId(page, userId);
        List<UserReplyResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), UserReplyResponseVo.class);
        responseVoList.forEach(item -> {
            item.setReplierNickname(user.getNickname());
            item.setShortContent(StringUtils.substring(item.getContentText(), 0, 500));

            Topic topic = topicService.getNotDeletedTopicByTopicId(item.getTopicId());
            item.setTopicTitle(topic.getTitle());

            item.setSubmitterUserId(topic.getSubmitterUserId());

            User submitter = userService.getUserByUserId(topic.getSubmitterUserId());
            item.setSubmitterNickname(submitter.getNickname());
            item.setSubmitterAvatarPath(submitter.getAvatarPath());

            item.setContent(null);
        });
        return new PageResultVo<>(responseVoList, pageResult);
    }

    public PageResultVo<ReplyResponseVo> getReplyList(Long boardId, Long topicId, String username, String keyword,
                                                      Date startTime, Date endTime, long pageIndex, long pageSize) {
        Long replierUserId = null;
        if (!StringUtils.isBlank(username)) {
            User user = userService.getNotDeletedUserByUsername(username.trim());
            if (user != null) {
                replierUserId = user.getId();
            } else {
                replierUserId = -1L;
            }
        }

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Reply> pageResult = replyService.listNotDeletedRepliesByPage(page, boardId, topicId,
                replierUserId, keyword, startTime, endTime);
        List<ReplyResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), ReplyResponseVo.class);
        responseVoList.forEach(item -> {
            Topic topic = topicService.getNotDeletedTopicByTopicId(item.getTopicId());
            item.setTopicTitle(topic.getTitle());
            item.setShortContent(StringUtils.substring(item.getContentText(), 0, 500));

            User replier = userService.getNotDeletedUserByUserId(item.getReplierUserId());
            item.setReplierUsername(replier.getNickname());
            item.setReplierNickname(replier.getAvatarPath());

            User editor = userService.getUserByUserId(item.getEditorUserId());
            if (editor != null) {
                item.setEditorUsername(editor.getNickname());
                item.setEditorNickname(editor.getAvatarPath());
            }
        });
        return new PageResultVo<>(responseVoList, pageResult);
    }

    public UserReplyResponseVo getReplyDetail(Long replyId) {
        Reply reply = replyService.getReplyByReplyId(replyId);
        if (reply == null) {
            throw new BusinessException(BusinessErrorEnum.REPLY_NOT_FOUND);
        }
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(reply.getBoardId());
        if (permission.getBanVisit()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        UserReplyResponseVo responseVo = mapperFacade.map(reply, UserReplyResponseVo.class);
        User replier = userService.getNotDeletedUserByUserId(reply.getReplierUserId());
        responseVo.setReplierNickname(replier.getNickname());
        responseVo.setShortContent(StringUtils.substring(responseVo.getContentText(), 0, 500));

        Topic topic = topicService.getNotDeletedTopicByTopicId(responseVo.getTopicId());
        responseVo.setTopicTitle(topic.getTitle());

        responseVo.setSubmitterUserId(topic.getSubmitterUserId());

        User submitter = userService.getUserByUserId(topic.getSubmitterUserId());
        responseVo.setSubmitterNickname(submitter.getNickname());
        responseVo.setSubmitterAvatarPath(submitter.getAvatarPath());

        return responseVo;
    }

}
