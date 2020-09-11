package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.ManageTopicActionEnum;
import cn.wegfan.forum.constant.TopicListSortEnum;
import cn.wegfan.forum.constant.TopicListTypeEnum;
import cn.wegfan.forum.mapper.TopicMapper;
import cn.wegfan.forum.model.entity.Topic;
import cn.wegfan.forum.util.EscapeUtil;
import cn.wegfan.forum.util.HtmlUtil;
import cn.wegfan.forum.util.IpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "topic")
@Transactional(rollbackFor = Exception.class)
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BoardService boardService;

    @Override
    public Topic getNotDeletedTopicByTopicId(Long topicId) {
        return topicMapper.selectNotDeletedByTopicId(topicId);
    }

    @Override
    public int addTopic(Topic topic) {
        Date now = new Date();
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        String ip = IpUtil.getIpAddress();
        topic.setCategoryId(boardService.getNotDeletedBoardByBoardId(topic.getBoardId()).getCategoryId());
        topic.setTitle(topic.getTitle().trim());
        topic.setContentText(HtmlUtil.getInnerTextWithImageLabel(topic.getContent()));
        topic.setSubmitTime(now);
        topic.setSubmitterUserId(userId);
        topic.setSubmitterIp(ip);
        topic.setViewCount(0L);
        topic.setReplyCount(0L);
        topic.setLastReplierUserId(userId);
        topic.setLastReplyTime(now);
        topic.setLastReplierIp(ip);
        topic.setPinned(false);
        topic.setFeatured(false);
        return topicMapper.insert(topic);
    }

    @Override
    public int updateTopic(Topic topic) {
        Topic modified = new Topic();
        modified.setId(topic.getId());
        Date now = new Date();
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        modified.setTitle(topic.getTitle().trim());
        modified.setType(topic.getType());
        modified.setContent(topic.getContent());
        modified.setContentText(HtmlUtil.getInnerTextWithImageLabel(topic.getContent()));
        modified.setEditorUserId(userId);
        modified.setEditorIp(IpUtil.getIpAddress());
        modified.setEditTime(now);
        return topicMapper.updateById(modified);
    }

    @Override
    public int updateTopicLastReplyByTopicId(Long topicId) {
        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setLastReplyTime(new Date());
        topic.setLastReplierUserId((Long)SecurityUtils.getSubject().getPrincipal());
        topic.setLastReplierIp(IpUtil.getIpAddress());
        return topicMapper.updateById(topic);
    }

    @Override
    public int batchCascadeDeleteTopic(Long submitterUserId, Long categoryId, Long boardId) {
        return topicMapper.batchCascadeDelete(submitterUserId, categoryId, boardId);
    }

    @Override
    public boolean batchDeleteTopicByTopicIds(List<Long> idList) {
        if (idList.isEmpty()) {
            return false;
        }
        Date now = new Date();
        return update(Wrappers.<Topic>lambdaUpdate()
                .set(Topic::getDeleteTime, now)
                .in(Topic::getId, idList));
    }

    @Override
    public Page<Topic> listNotDeletedTopicsByPageAndBoardId(Page<?> page, Long boardId, TopicListTypeEnum typeEnum, TopicListSortEnum sortEnum) {
        return listNotDeletedTopicsByPage(page, boardId, null, typeEnum,
                null, null, null, sortEnum);
    }

    @Override
    public Page<Topic> listNotDeletedTopicsByPageAndSubmitterUserId(Page<?> page, Long userId, TopicListTypeEnum typeEnum, TopicListSortEnum sortEnum) {
        return listNotDeletedTopicsByPage(page, (List<Long>)null, userId, typeEnum,
                null, null, null, sortEnum);
    }

    @Override
    public Page<Topic> listNotDeletedTopicsByPage(Page<?> page, Long boardId, Long submitterUserId, TopicListTypeEnum typeEnum,
                                                  String keyword, Date startTime, Date endTime, TopicListSortEnum sortEnum) {
        return listNotDeletedTopicsByPage(page, Collections.singletonList(boardId), submitterUserId, typeEnum,
                keyword, startTime, endTime, sortEnum);
    }

    @Override
    public Page<Topic> listNotDeletedTopicsByPage(Page<?> page, List<Long> boardIdList, Long submitterUserId, TopicListTypeEnum typeEnum,
                                                  String keyword, Date startTime, Date endTime, TopicListSortEnum sortEnum) {
        if (StringUtils.isEmpty(keyword)) {
            keyword = null;
        } else {
            keyword = EscapeUtil.escapeSqlLike(keyword.trim());
        }
        return topicMapper.selectNotDeletedByPage(page, boardIdList, submitterUserId, typeEnum,
                keyword, startTime, endTime, sortEnum);
    }

    @Override
    public int batchManageTopicByTopicIdsAndAction(List<Long> topicIdList, ManageTopicActionEnum actionEnum) {
        if (topicIdList.isEmpty()) {
            return 0;
        }
        return topicMapper.batchManageByTopicIdListAndAction(topicIdList, actionEnum);
    }

    @Override
    public int increaseTopicViewCountByTopicId(Long topicId) {
        return topicMapper.increaseTopicViewCountByTopicId(topicId);
    }

    @Override
    public int increaseTopicReplyCountByTopicId(Long topicId) {
        return topicMapper.increaseTopicReplyCountByTopicId(topicId);
    }

}
