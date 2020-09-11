package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Attachment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttachmentMapper extends BaseMapper<Attachment> {

    Attachment selectNotDeletedByAttachmentId(Long attachmentId);

    List<Attachment> selectNotDeletedListByTopicId(Long topicId);

    int updateDescriptionByAttachmentId(Long attachmentId, String description);

    Attachment selectNotDeletedByFilePath(String filePath);

    int increaseDownloadCountByAttachmentId(Long attachmentId);

    Page<Attachment> selectNotDeletedListByPage(Page<?> page, Long boardId, Long uploaderUserId, String filename,
                                                Date startTime, Date endTime);

    int deleteByAttachmentId(Long attachmentId);

    int deleteByTopicId(Long topicId);

    int deleteByBoardId(Long boardId);

    int deleteByUploaderUserId(Long uploaderUserId);

    int deleteByCategoryId(Long categoryId);

}
