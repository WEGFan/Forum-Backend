package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.ManageTopicActionEnum;
import cn.wegfan.forum.constant.TopicListSortEnum;
import cn.wegfan.forum.constant.TopicListTypeEnum;
import cn.wegfan.forum.model.entity.Topic;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface TopicService extends IService<Topic> {

    Topic getNotDeletedTopicByTopicId(Long topicId);

    int addTopic(Topic topic);

    int updateTopic(Topic topic);

    int updateTopicLastReplyByTopicId(Long topicId);

    int batchCascadeDeleteTopic(Long submitterUserId, Long categoryId, Long boardId);

    boolean batchDeleteTopicByTopicIds(List<Long> idList);

    Page<Topic> listNotDeletedTopicsByPageAndBoardId(Page<?> page, Long boardId, TopicListTypeEnum typeEnum, TopicListSortEnum sortEnum);

    Page<Topic> listNotDeletedTopicsByPageAndSubmitterUserId(Page<?> page, Long userId, TopicListTypeEnum typeEnum, TopicListSortEnum sortEnum);

    Page<Topic> listNotDeletedTopicsByPage(Page<?> page, Long boardId, Long submitterUserId, TopicListTypeEnum typeEnum,
                                           String keyword, Date startTime, Date endTime, TopicListSortEnum sortEnum);

    Page<Topic> listNotDeletedTopicsByPage(Page<?> page, List<Long> boardIdList, Long submitterUserId, TopicListTypeEnum typeEnum,
                                           String keyword, Date startTime, Date endTime, TopicListSortEnum sortEnum);

    int batchManageTopicByTopicIdsAndAction(List<Long> topicIdList, ManageTopicActionEnum actionEnum);

    int increaseTopicViewCountByTopicId(Long topicId);

    int increaseTopicReplyCountByTopicId(Long topicId);

}
