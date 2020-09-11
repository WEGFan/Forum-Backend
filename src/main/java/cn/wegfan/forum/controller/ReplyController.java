package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.TopicReplyListSortEnum;
import cn.wegfan.forum.model.vo.request.AddReplyRequestVo;
import cn.wegfan.forum.model.vo.request.IdListRequestVo;
import cn.wegfan.forum.model.vo.request.IdRequestVo;
import cn.wegfan.forum.model.vo.request.UpdateReplyRequestVo;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.ReplyServiceFacade;
import cn.wegfan.forum.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ReplyServiceFacade replyServiceFacade;

    /**
     * 查看指定主题帖的回复帖
     */
    @GetMapping("topic-reply-list")
    public ResultVo getTopicReplyList(@RequestParam Long topicId,
                                      @RequestParam(required = false) Boolean submitterOnly,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam Long page,
                                      @RequestParam Long count) {
        count = PaginationUtil.clampPageSize(count);
        TopicReplyListSortEnum sortEnum = TopicReplyListSortEnum.fromValue(sort,
                TopicReplyListSortEnum.REPLY_TIME_ASC);
        return ResultVo.success(replyServiceFacade.getTopicReplyList(topicId, submitterOnly,
                sortEnum, page, count));
    }

    /**
     * 查看指定用户的回复帖列表
     */
    @GetMapping("user-reply-list")
    public ResultVo getUserReplyList(@RequestParam Long userId,
                                     @RequestParam Long page,
                                     @RequestParam Long count) {
        count = PaginationUtil.clampPageSize(count);
        return ResultVo.success(replyServiceFacade.getUserReplyList(userId, page, count));
    }

    /**
     * 【管理】查看所有回复帖列表
     */
    @GetMapping("reply-list")
    @RequiresUser
    public ResultVo getReplyList(@RequestParam(required = false) Long boardId,
                                 @RequestParam(required = false) Long topicId,
                                 @RequestParam(required = false) String username,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Date from,
                                 @RequestParam(required = false) Date to,
                                 @RequestParam Long page,
                                 @RequestParam Long count) {
        count = PaginationUtil.clampPageSize(count);
        return ResultVo.success(replyServiceFacade.getReplyList(boardId, topicId, username,
                keyword, from, to, page, count));
    }

    /**
     * 查看回复帖详情
     */
    @GetMapping("reply-detail")
    public ResultVo getReplyList(@RequestParam Long replyId) {
        return ResultVo.success(replyServiceFacade.getReplyDetail(replyId));
    }

    /**
     * 发表回复帖
     */
    @PostMapping("add-reply")
    @RequiresUser
    public ResultVo addReply(@Valid @RequestBody AddReplyRequestVo requestVo) {
        replyServiceFacade.addReply(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 编辑回复帖
     */
    @PostMapping("update-reply")
    @RequiresUser
    public ResultVo updateReply(@Valid @RequestBody UpdateReplyRequestVo requestVo) {
        replyServiceFacade.updateReply(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】批量删除回复帖
     */
    @PostMapping("batch-delete-reply")
    @RequiresUser
    public ResultVo batchDeleteReply(@Valid @RequestBody IdListRequestVo requestVo) {
        replyServiceFacade.batchDeleteReply(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 发帖者删除回复帖
     */
    @PostMapping("delete-reply")
    @RequiresUser
    public ResultVo deleteReply(@Valid @RequestBody IdRequestVo requestVo) {
        replyServiceFacade.deleteReply(requestVo);
        return ResultVo.success(null);
    }

}
