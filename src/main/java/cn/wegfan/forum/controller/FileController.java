package cn.wegfan.forum.controller;

import cn.hutool.core.io.FileUtil;
import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.model.entity.Attachment;
import cn.wegfan.forum.service.AttachmentServiceFacade;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@RestController
public class FileController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private AttachmentServiceFacade attachmentServiceFacade;

    /**
     * 获取头像图片
     */
    @GetMapping(value = Constant.AVATAR_API_ENDPOINT + "{filename}", produces = {
            MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE
    })
    public byte[] getAvatarFile(@PathVariable String filename) {
        File file = Constant.AVATAR_PATH.resolve(filename).toFile();
        log.debug(file.toString());
        if (!FileUtil.exist(file)) {
            throw new BusinessException(BusinessErrorEnum.FILE_NOT_FOUND);
        }
        return FileUtil.readBytes(file);
    }

    /**
     * 获取主题帖和回复帖的图片
     */
    @GetMapping(value = Constant.IMAGE_API_ENDPOINT + "{filename}", produces = {
            MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE
    })
    public byte[] getImageFile(@PathVariable String filename) {
        File file = Constant.IMAGE_PATH.resolve(filename).toFile();
        log.debug(file.toString());
        if (!FileUtil.exist(file)) {
            throw new BusinessException(BusinessErrorEnum.FILE_NOT_FOUND);
        }

        return FileUtil.readBytes(file);
    }

    /**
     * 获取附件文件
     */
    @GetMapping(Constant.ATTACHMENT_API_ENDPOINT + "{filename}")
    @RequiresUser
    public ResponseEntity<Resource> getAttachmentFile(@PathVariable String filename) throws UnsupportedEncodingException {
        Attachment attachment = attachmentServiceFacade.downloadAttachment(filename);

        byte[] data = FileUtil.readBytes(Constant.ATTACHMENT_PATH.resolve(filename).toFile());

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + URLEncoder.encode(FileUtil.getName(attachment.getFilename()), "UTF-8"))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(data.length)
                .body(resource);
    }

}
