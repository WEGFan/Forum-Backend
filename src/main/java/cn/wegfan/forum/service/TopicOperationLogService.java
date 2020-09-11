package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.ManageTopicActionEnum;
import cn.wegfan.forum.model.entity.TopicOperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TopicOperationLogService extends IService<TopicOperationLog> {

    boolean batchAddTopicOperationLog(List<Long> topicIdList, ManageTopicActionEnum actionEnum, String reason);

    List<TopicOperationLog> listTopicOperationLogsByTopicId(Long topicId);

}
