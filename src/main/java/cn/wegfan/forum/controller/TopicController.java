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
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 查看指定板块的主题帖列表
     */
    @GetMapping("board-topic-list")
    public ResultVo getBoardTopicList(@RequestParam Integer boardId,
                                      @RequestParam(required = false) String type,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam Integer page,
                                      @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 查看指定用户的主题帖列表
     */
    @GetMapping("user-topic-list")
    public ResultVo getUserTopicList(@RequestParam Integer userId,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) String sort,
                                     @RequestParam Integer page,
                                     @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】查看所有主题帖列表
     */
    @GetMapping("topic-list")
    public ResultVo getTopicList(@RequestParam(required = false) Integer boardId,
                                 @RequestParam(required = false) String username,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Date from,
                                 @RequestParam(required = false) Date to,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam Integer page,
                                 @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 查看指定主题帖详细信息
     */
    @GetMapping("topic-detail")
    public ResultVo getTopicDetail(@RequestParam Integer topicId) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 发布主题帖
     */
    @PostMapping("add-topic")
    public ResultVo addTopic() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 编辑主题帖
     */
    @PostMapping("update-topic")
    public ResultVo updateTopic() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】主题帖管理（单个+批量）
     */
    @PostMapping("manage-topic")
    public ResultVo adminManageTopic() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 发帖者删除主题帖
     */
    @PostMapping("delete-topic")
    public ResultVo deleteTopic() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 查看指定主题帖的操作记录
     */
    @GetMapping("topic-operation-log")
    public ResultVo getTopicOperationLog(@RequestParam Integer topicId) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

}
