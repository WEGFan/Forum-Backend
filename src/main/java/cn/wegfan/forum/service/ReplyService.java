package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.TopicReplyListSortEnum;
import cn.wegfan.forum.model.entity.Reply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface ReplyService extends IService<Reply> {

    int addReply(Reply reply);

    int updateReply(Reply reply);

    Reply getReplyByReplyId(Long replyId);

    boolean batchDeleteReplyByReplyIdList(List<Long> replyIdList);

    boolean batchCascadeDeleteReply(Long replierUserId, Long topicId, Long categoryId, Long boardId);

    Page<Reply> listNotDeletedRepliesByPageAndTopicId(Page<?> page, Long topicId, Long replierUserId,
                                                      TopicReplyListSortEnum sortEnum);

    Page<Reply> listNotDeletedRepliesByPageAndReplierUserId(Page<?> page, Long replierUserId);

    Page<Reply> listNotDeletedRepliesByPage(Page<?> page, Long boardId, Long topicId, Long replierUserId,
                                            String keyword, Date startTime, Date endTime);

}
