package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.Attachment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AttachmentService extends IService<Attachment> {

    int addAttachment(Attachment attachment);

    Attachment getNotDeletedAttachmentByAttachmentId(Long attachmentId);

    boolean batchAddAttachmentTopicAndBoardIdByAttachmentIdList(Long topicId, Long boardId, Set<Long> attachmentIdList);

    boolean batchDeleteAttachmentsByAttachmentIdList(Set<Long> attachmentIdList);

    void batchUpdateAttachmentDescription(Map<Long, String> attachmentIdDescriptionMap);

    List<Attachment> listNotDeletedAttachmentsByTopicId(Long topicId);

    List<Attachment> listNotDeletedAttachmentsByAttachmentIdList(Set<Long> attachmentIdList);

    Attachment getNotDeletedAttachmentByFilePath(String filePath);

    int increaseAttachmentDownloadCountByAttachmentId(Long attachmentId);

    Page<Attachment> listNotDeletedAttachmentsByPage(Page<?> page, Long boardId, Long uploaderUserId, String filename,
                                                     Date startTime, Date endTime);

    int deleteAttachmentByAttachmentId(Long attachmentId);

    boolean batchDeleteAttachmentByTopicIds(List<Long> topicIdList);

    boolean batchDeleteAttachmentByBoardIds(List<Long> boardIdList);

    int batchDeleteAttachmentByUploaderUserId(Long uploaderUserId);

    int deleteAttachmentByCategoryId(Long categoryId);

}
