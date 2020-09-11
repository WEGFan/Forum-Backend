package cn.wegfan.forum.mapper;

import cn.wegfan.forum.constant.TopicReplyListSortEnum;
import cn.wegfan.forum.model.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReplyMapper extends BaseMapper<Reply> {

    Reply selectByReplyId(Long replyId);

    boolean batchCascadeDelete(Long replierUserId, Long topicId, Long categoryId, Long boardId);

    Page<Reply> selectNotDeletedListByTopicIdAndPage(Page<?> page, Long topicId, Long replierUserId,
                                                     TopicReplyListSortEnum sortEnum);

    Page<Reply> selectNotDeletedListByPageAndReplierUserId(Page<?> page, Long replierUserId);

    Page<Reply> selectNotDeletedListByPage(Page<?> page, Long boardId, Long topicId, Long replierUserId, String keyword,
                                           Date startTime, Date endTime);

}
