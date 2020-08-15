package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 【管理】查看附件列表
     */
    @GetMapping("attachment-list")
    public ResultVo getAttachmentList(@RequestParam(required = false) Integer boardId,
                                      @RequestParam(required = false) String username,
                                      @RequestParam(required = false) String filename,
                                      @RequestParam(required = false) Date from,
                                      @RequestParam(required = false) Date to,
                                      @RequestParam Integer page,
                                      @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 上传图片
     */
    @PostMapping("upload-image")
    public ResultVo uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 上传附件
     */
    @PostMapping("upload-attachment")
    public ResultVo uploadAttachment(@RequestParam("file") MultipartFile multipartFile) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】删除附件
     */
    @PostMapping("delete-attachment")
    public ResultVo deleteAttachment() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

}
