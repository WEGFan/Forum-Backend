package cn.wegfan.forum.service;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.model.entity.Attachment;
import cn.wegfan.forum.model.entity.Board;
import cn.wegfan.forum.model.entity.Topic;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.IdListRequestVo;
import cn.wegfan.forum.model.vo.response.*;
import cn.wegfan.forum.util.BusinessException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AttachmentServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UserServiceFacade userServiceFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TopicService topicService;

    public PageResultVo<AttachmentResponseVo> getAttachmentList(Long boardId, String username, String filename,
                                                                Date startTime, Date endTime, long pageIndex, long pageSize) {
        Long userId = null;
        if (!StringUtils.isBlank(username)) {
            User user = userService.getNotDeletedUserByUsername(username.trim());
            if (user != null) {
                userId = user.getId();
            } else {
                userId = -1L;
            }
        }

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Attachment> pageResult = attachmentService.listNotDeletedAttachmentsByPage(page, boardId,
                userId, filename, startTime, endTime);
        List<AttachmentResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), AttachmentResponseVo.class);
        responseVoList.forEach(item -> {
            User uploader = userService.getNotDeletedUserByUserId(item.getUploaderUserId());
            if (uploader != null) {
                item.setUploaderUsername(uploader.getUsername());
            }

            Board board = boardService.getNotDeletedBoardByBoardId(item.getBoardId());
            if (board != null) {
                item.setBoardName(board.getName());
            }

            Topic topic = topicService.getNotDeletedTopicByTopicId(item.getTopicId());
            if (topic != null) {
                item.setTopicTitle(topic.getTitle());
            }
        });

        return new PageResultVo<>(responseVoList, pageResult);
    }

    public UploadAttachmentResponseVo uploadAttachment(MultipartFile multipartFile) throws IOException {
        if (multipartFile.getSize() > Constant.MAX_ATTACHMENT_SIZE.toBytes()) {
            throw new BusinessException(BusinessErrorEnum.UPLOAD_FILE_TOO_LARGE);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        log.debug("{}", originalFilename);

        String extension = FileUtil.extName(originalFilename);
        String filename = UUID.randomUUID(true).toString(true) +
                (ObjectUtils.isEmpty(extension) ? "" : "." + extension);
        if (originalFilename == null) {
            originalFilename = filename;
        }

        File file = Constant.ATTACHMENT_PATH.resolve(filename).toFile();
        multipartFile.transferTo(file);

        Attachment attachment = new Attachment();
        attachment.setFilename(originalFilename);
        attachment.setFileSize((int)multipartFile.getSize());
        attachment.setFilePath(filename);
        attachmentService.addAttachment(attachment);

        UploadAttachmentResponseVo responseVo = mapperFacade.map(attachment, UploadAttachmentResponseVo.class);
        return responseVo;
    }

    public ImageUrlResponseVo uploadImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.getSize() > Constant.MAX_IMAGE_SIZE.toBytes()) {
            throw new BusinessException(BusinessErrorEnum.UPLOAD_FILE_TOO_LARGE);
        }

        String filename = UUID.randomUUID(true).toString(true);
        File tempFile = File.createTempFile(Constant.TEMP_FILE_PREFIX, null);

        multipartFile.transferTo(tempFile);

        Tika tika = new Tika();
        String fileType = tika.detect(tempFile);

        if (!ArrayUtils.contains(Constant.ALLOWED_IMAGE_MEDIA_TYPES, fileType)) {
            throw new BusinessException(BusinessErrorEnum.UPLOAD_FILE_TYPE_NOT_ALLOWED);
        }

        String extension = FileTypeUtil.getType(tempFile);

        filename = filename + "." + extension;
        File file = Constant.IMAGE_PATH.resolve(filename).toFile();
        FileUtil.move(tempFile, file, true);

        return new ImageUrlResponseVo(Constant.IMAGE_API_ENDPOINT + filename);
    }

    public Attachment downloadAttachment(String filePath) {
        Attachment attachment = attachmentService.getNotDeletedAttachmentByFilePath(filePath);
        if (attachment == null) {
            throw new BusinessException(BusinessErrorEnum.FILE_NOT_FOUND);
        }
        UserBoardPermissionResponseVo permission = userServiceFacade.getUserBoardPermission(attachment.getBoardId());
        if (permission.getBanDownloadAttachment()) {
            throw new BusinessException(BusinessErrorEnum.UNAUTHORIZED);
        }

        attachmentService.increaseAttachmentDownloadCountByAttachmentId(attachment.getId());
        return attachment;
    }

    public void deleteAttachment(IdListRequestVo requestVo) {
        Set<Long> attachmentIdList = new HashSet<>(requestVo.getIdList());

        List<Attachment> attachmentList = attachmentService.listNotDeletedAttachmentsByAttachmentIdList(attachmentIdList);

        attachmentService.batchDeleteAttachmentsByAttachmentIdList(attachmentIdList);
        for (Attachment attachment : attachmentList) {
            File attachmentFile = Constant.ATTACHMENT_PATH.resolve(attachment.getFilePath()).toFile();
            boolean isFileDeleteSuccessful = FileUtil.del(attachmentFile);
            if (!isFileDeleteSuccessful) {
                log.warn("删除附件失败：{}", attachmentFile);
            }
        }
    }

}
