package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.TopicReplyListSortEnum;
import cn.wegfan.forum.mapper.ReplyMapper;
import cn.wegfan.forum.model.entity.Reply;
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

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "reply")
@Transactional(rollbackFor = Exception.class)
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private TopicService topicService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public int addReply(Reply reply) {
        Date now = new Date();
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        reply.setReplierUserId(userId);
        reply.setReplierIp(IpUtil.getIpAddress());
        reply.setReplyTime(now);

        Topic topic = topicService.getNotDeletedTopicByTopicId(reply.getTopicId());
        reply.setBoardId(topic.getBoardId());
        reply.setCategoryId(topic.getCategoryId());

        reply.setContentText(HtmlUtil.getInnerTextWithImageLabel(reply.getContent()));
        return replyMapper.insert(reply);
    }

    @Override
    public int updateReply(Reply reply) {
        Reply modified = new Reply();
        modified.setId(reply.getId());
        Date now = new Date();
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        modified.setContent(reply.getContent());
        modified.setContentText(HtmlUtil.getInnerTextWithImageLabel(reply.getContent()));
        modified.setEditorUserId(userId);
        modified.setEditorIp(IpUtil.getIpAddress());
        modified.setEditTime(now);
        return replyMapper.updateById(modified);
    }

    @Override
    public Reply getReplyByReplyId(Long replyId) {
        return replyMapper.selectByReplyId(replyId);
    }

    @Override
    public boolean batchDeleteReplyByReplyIdList(List<Long> replyIdList) {
        if (replyIdList.isEmpty()) {
            return false;
        }
        Date now = new Date();
        return update(Wrappers.<Reply>lambdaUpdate()
                .set(Reply::getDeleteTime, now)
                .in(Reply::getId, replyIdList));
    }

    @Override
    public boolean batchCascadeDeleteReply(Long replierUserId, Long topicId, Long categoryId, Long boardId) {
        return replyMapper.batchCascadeDelete(replierUserId, topicId, categoryId, boardId);
    }

    @Override
    public Page<Reply> listNotDeletedRepliesByPageAndTopicId(Page<?> page, Long topicId, Long replierUserId, TopicReplyListSortEnum sortEnum) {
        return replyMapper.selectNotDeletedListByTopicIdAndPage(page, topicId, replierUserId, sortEnum);
    }

    @Override
    public Page<Reply> listNotDeletedRepliesByPageAndReplierUserId(Page<?> page, Long replierUserId) {
        return replyMapper.selectNotDeletedListByPageAndReplierUserId(page, replierUserId);
    }

    @Override
    public Page<Reply> listNotDeletedRepliesByPage(Page<?> page, Long boardId, Long topicId, Long replierUserId, String keyword, Date startTime, Date endTime) {
        if (StringUtils.isEmpty(keyword)) {
            keyword = null;
        } else {
            keyword = EscapeUtil.escapeSqlLike(keyword.trim());
        }
        return replyMapper.selectNotDeletedListByPage(page, boardId, topicId, replierUserId,
                keyword, startTime, endTime);
    }

}
