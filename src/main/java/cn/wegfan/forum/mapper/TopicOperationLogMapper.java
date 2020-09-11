package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.TopicOperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicOperationLogMapper extends BaseMapper<TopicOperationLog> {

    List<TopicOperationLog> selectListByTopicId(Long topicId);

}
