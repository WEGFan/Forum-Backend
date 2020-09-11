package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.mapper.AttachmentMapper;
import cn.wegfan.forum.model.entity.Attachment;
import cn.wegfan.forum.util.EscapeUtil;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@CacheConfig(cacheNames = "attachment")
@Transactional(rollbackFor = Exception.class)
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public int addAttachment(Attachment attachment) {
        attachment.setDescription("");
        attachment.setDownloadCount(0L);
        attachment.setUploadTime(new Date());
        attachment.setUploaderIp(IpUtil.getIpAddress());
        attachment.setUploaderUserId((Long)SecurityUtils.getSubject().getPrincipal());
        attachment.setDownloadUrl(Constant.ATTACHMENT_API_ENDPOINT + attachment.getFilePath());
        return attachmentMapper.insert(attachment);
    }

    @Override
    public Attachment getNotDeletedAttachmentByAttachmentId(Long attachmentId) {
        return attachmentMapper.selectNotDeletedByAttachmentId(attachmentId);
    }

    @Override
    public boolean batchAddAttachmentTopicAndBoardIdByAttachmentIdList(Long topicId, Long boardId, Set<Long> attachmentIdList) {
        if (attachmentIdList.isEmpty()) {
            return false;
        }
        return update(Wrappers.<Attachment>lambdaUpdate()
                .set(Attachment::getTopicId, topicId)
                .set(Attachment::getBoardId, boardId)
                .in(Attachment::getId, attachmentIdList));
    }

    @Override
    public void batchUpdateAttachmentDescription(Map<Long, String> attachmentIdDescriptionMap) {
        for (Map.Entry<Long, String> entry : attachmentIdDescriptionMap.entrySet()) {
            attachmentMapper.updateDescriptionByAttachmentId(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean batchDeleteAttachmentsByAttachmentIdList(Set<Long> attachmentIdList) {
        if (attachmentIdList.isEmpty()) {
            return false;
        }
        Date now = new Date();
        return update(Wrappers.<Attachment>lambdaUpdate()
                .set(Attachment::getDeleteTime, now)
                .in(Attachment::getId, attachmentIdList));
    }

    @Override
    public List<Attachment> listNotDeletedAttachmentsByTopicId(Long topicId) {
        return attachmentMapper.selectNotDeletedListByTopicId(topicId);
    }

    @Override
    public List<Attachment> listNotDeletedAttachmentsByAttachmentIdList(Set<Long> attachmentIdList) {
        if (attachmentIdList.isEmpty()) {
            return Collections.emptyList();
        }
        return list(Wrappers.<Attachment>lambdaQuery()
                .isNull(Attachment::getDeleteTime)
                .in(Attachment::getId, attachmentIdList));
    }

    @Override
    public Attachment getNotDeletedAttachmentByFilePath(String filePath) {
        return attachmentMapper.selectNotDeletedByFilePath(filePath);
    }

    @Override
    public int increaseAttachmentDownloadCountByAttachmentId(Long attachmentId) {
        return attachmentMapper.increaseDownloadCountByAttachmentId(attachmentId);
    }

    @Override
    public Page<Attachment> listNotDeletedAttachmentsByPage(Page<?> page, Long boardId, Long uploaderUserId,
                                                            String filename, Date startTime, Date endTime) {
        if (StringUtils.isEmpty(filename)) {
            filename = null;
        } else {
            filename = filename.trim();
            filename = EscapeUtil.escapeSqlLike(filename);
        }
        return attachmentMapper.selectNotDeletedListByPage(page, boardId, uploaderUserId,
                filename, startTime, endTime);
    }

    @Override
    public int deleteAttachmentByAttachmentId(Long attachmentId) {
        return attachmentMapper.deleteByAttachmentId(attachmentId);
    }

    @Override
    public boolean batchDeleteAttachmentByTopicIds(List<Long> topicIdList) {
        if (topicIdList.isEmpty()) {
            return false;
        }
        return update(Wrappers.<Attachment>lambdaUpdate()
                .set(Attachment::getDeleteTime, new Date())
                .in(Attachment::getTopicId, topicIdList));
    }

    @Override
    public boolean batchDeleteAttachmentByBoardIds(List<Long> boardIdList) {
        if (boardIdList.isEmpty()) {
            return false;
        }
        return update(Wrappers.<Attachment>lambdaUpdate()
                .set(Attachment::getDeleteTime, new Date())
                .in(Attachment::getBoardId, boardIdList));
    }

    @Override
    public int batchDeleteAttachmentByUploaderUserId(Long uploaderUserId) {
        return attachmentMapper.deleteByUploaderUserId(uploaderUserId);
    }

    @Override
    public int deleteAttachmentByCategoryId(Long categoryId) {
        return attachmentMapper.deleteByCategoryId(categoryId);
    }

}
