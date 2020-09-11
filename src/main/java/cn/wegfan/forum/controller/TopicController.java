package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.ManageTopicActionEnum;
import cn.wegfan.forum.constant.TopicListSortEnum;
import cn.wegfan.forum.constant.TopicListTypeEnum;
import cn.wegfan.forum.model.vo.request.AddTopicRequestVo;
import cn.wegfan.forum.model.vo.request.IdRequestVo;
import cn.wegfan.forum.model.vo.request.ManageTopicRequestVo;
import cn.wegfan.forum.model.vo.request.UpdateTopicRequestVo;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.TopicServiceFacade;
import cn.wegfan.forum.util.PaginationUtil;
import cn.wegfan.forum.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private TopicServiceFacade topicServiceFacade;

    /**
     * 查看指定板块的主题帖列表
     */
    @GetMapping("board-topic-list")
    public ResultVo getBoardTopicList(@RequestParam Long boardId,
                                      @RequestParam(required = false) String type,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam Long page,
                                      @RequestParam Long count) {
        TopicListTypeEnum topicTypeEnum = TopicListTypeEnum.fromValue(type, TopicListTypeEnum.NORMAL_AND_FEATURED);
        TopicListSortEnum topicSortEnum = TopicListSortEnum.fromValue(sort, TopicListSortEnum.REPLY_TIME);
        count = PaginationUtil.clampPageSize(count);
        return ResultVo.success(topicServiceFacade.getBoardTopicList(boardId, topicTypeEnum,
                topicSortEnum, page, count));
    }

    /**
     * 查看指定用户的主题帖列表
     */
    @GetMapping("user-topic-list")
    public ResultVo getUserTopicList(@RequestParam Long userId,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) String sort,
                                     @RequestParam Long page,
                                     @RequestParam Long count) {
        TopicListTypeEnum topicTypeEnum = TopicListTypeEnum.fromValue(type, TopicListTypeEnum.NORMAL_AND_FEATURED);
        TopicListSortEnum topicSortEnum = TopicListSortEnum.fromValue(sort, TopicListSortEnum.REPLY_TIME);
        count = PaginationUtil.clampPageSize(count);
        return ResultVo.success(topicServiceFacade.getUserTopicList(userId, topicTypeEnum,
                topicSortEnum, page, count));
    }

    /**
     * 【管理】查看所有主题帖列表
     */
    @GetMapping("topic-list")
    @RequiresUser
    public ResultVo getTopicList(@RequestParam(required = false) Long boardId,
                                 @RequestParam(required = false) String username,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Date from,
                                 @RequestParam(required = false) Date to,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam Long page,
                                 @RequestParam Long count) {
        TopicListTypeEnum topicTypeEnum = TopicListTypeEnum.fromValue(type, TopicListTypeEnum.ALL);
        TopicListSortEnum topicSortEnum = TopicListSortEnum.fromValue(sort, TopicListSortEnum.REPLY_TIME);
        count = PaginationUtil.clampPageSize(count);
        return ResultVo.success(topicServiceFacade.getTopicList(boardId, username,
                topicTypeEnum, keyword, from, to, topicSortEnum,
                page, count));
    }

    /**
     * 查看指定主题帖详细信息
     */
    @GetMapping("topic-detail")
    public ResultVo getTopicDetail(@RequestParam Long topicId) {
        return ResultVo.success(topicServiceFacade.getTopicDetail(topicId));
    }

    /**
     * 发布主题帖
     */
    @PostMapping("add-topic")
    @RequiresUser
    public ResultVo addTopic(@Valid @RequestBody AddTopicRequestVo requestVo) {
        return ResultVo.success(topicServiceFacade.addTopic(requestVo));
    }

    /**
     * 编辑主题帖
     */
    @PostMapping("update-topic")
    @RequiresUser
    public ResultVo updateTopic(@Valid @RequestBody UpdateTopicRequestVo requestVo) {
        topicServiceFacade.updateTopic(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】主题帖管理（单个+批量）
     */
    @PostMapping("manage-topic")
    @RequiresUser
    public ResultVo adminManageTopic(@RequestBody ManageTopicRequestVo requestVo) {
        ManageTopicActionEnum actionEnum = ManageTopicActionEnum.fromValue(requestVo.getAction(), null);
        if (actionEnum == null) {
            requestVo.setAction("");
        }
        if (actionEnum == ManageTopicActionEnum.DELETE) {
            requestVo.setReason("delete");
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        topicServiceFacade.manageTopic(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 发帖者删除主题帖
     */
    @PostMapping("delete-topic")
    @RequiresUser
    public ResultVo deleteTopic(@Valid @RequestBody IdRequestVo requestVo) {
        topicServiceFacade.deleteTopic(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 查看指定主题帖的操作记录
     */
    @GetMapping("topic-operation-log")
    @RequiresUser
    public ResultVo getTopicOperationLog(@RequestParam Long topicId) {
        return ResultVo.success(topicServiceFacade.getTopicOperationLog(topicId));
    }

}
