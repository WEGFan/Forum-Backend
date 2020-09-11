package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.ManageTopicActionEnum;
import cn.wegfan.forum.mapper.TopicOperationLogMapper;
import cn.wegfan.forum.model.entity.TopicOperationLog;
import cn.wegfan.forum.util.IpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = "topicOperationLog")
@Transactional(rollbackFor = Exception.class)
public class TopicOperationLogServiceImpl extends ServiceImpl<TopicOperationLogMapper, TopicOperationLog> implements TopicOperationLogService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private TopicOperationLogMapper topicOperationLogMapper;

    @Override
    public boolean batchAddTopicOperationLog(List<Long> topicIdList, ManageTopicActionEnum actionEnum, String reason) {
        Date now = new Date();
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        String ip = IpUtil.getIpAddress();
        List<TopicOperationLog> entityList = topicIdList.stream()
                .map(topicId -> {
                    TopicOperationLog entity = new TopicOperationLog();
                    entity.setTopicId(topicId);
                    entity.setOperateTime(now);
                    entity.setOperatorIp(ip);
                    entity.setOperateType(actionEnum);
                    entity.setOperatorUserId(userId);
                    entity.setReason(reason);
                    return entity;
                })
                .collect(Collectors.toList());
        return saveBatch(entityList);
    }

    @Override
    public List<TopicOperationLog> listTopicOperationLogsByTopicId(Long topicId) {
        return topicOperationLogMapper.selectListByTopicId(topicId);
    }

}
