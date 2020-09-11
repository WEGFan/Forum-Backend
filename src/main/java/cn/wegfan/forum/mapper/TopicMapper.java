package cn.wegfan.forum.mapper;

import cn.wegfan.forum.constant.ManageTopicActionEnum;
import cn.wegfan.forum.constant.TopicListSortEnum;
import cn.wegfan.forum.constant.TopicListTypeEnum;
import cn.wegfan.forum.model.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TopicMapper extends BaseMapper<Topic> {

    Topic selectNotDeletedByTopicId(Long topicId);

    Page<Topic> selectNotDeletedByPage(Page<?> page, List<Long> boardIdList, Long submitterUserId, TopicListTypeEnum typeEnum,
                                       String keyword, Date startTime, Date endTime, TopicListSortEnum sortEnum);

    int batchCascadeDelete(Long submitterUserId, Long categoryId, Long boardId);

    int deleteByTopicId(Long topicId);

    int batchManageByTopicIdListAndAction(List<Long> topicIdList, ManageTopicActionEnum actionEnum);

    int increaseTopicViewCountByTopicId(Long topicId);

    int increaseTopicReplyCountByTopicId(Long topicId);

}
