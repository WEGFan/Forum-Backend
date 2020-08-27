package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 查看指定主题帖的回复帖
     */
    @GetMapping("topic-reply-list")
    public ResultVo getTopicReplyList(@RequestParam Integer topicId,
                                      @RequestParam Integer page,
                                      @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 查看指定用户的回复帖列表
     */
    @GetMapping("user-reply-list")
    public ResultVo getUserReplyList(@RequestParam Integer userId,
                                     @RequestParam Integer page,
                                     @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 【管理】查看所有回复帖列表
     */
    @GetMapping("reply-list")
    public ResultVo getReplyList(@RequestParam(required = false) Integer boardId,
                                 @RequestParam(required = false) String username,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Date from,
                                 @RequestParam(required = false) Date to,
                                 @RequestParam Integer page,
                                 @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 发表回复帖
     */
    @PostMapping("add-reply")
    public ResultVo addReply() {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 编辑回复帖
     */
    @PostMapping("update-reply")
    public ResultVo updateReply() {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 【管理】批量删除回复帖
     */
    @PostMapping("batch-delete-reply")
    public ResultVo batchDeleteReply() {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 发帖者删除回复帖
     */
    @PostMapping("delete-reply")
    public ResultVo deleteReply() {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

}
