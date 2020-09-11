package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.model.vo.request.IdListRequestVo;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.AttachmentServiceFacade;
import cn.wegfan.forum.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private AttachmentServiceFacade attachmentServiceFacade;

    /**
     * 【管理】查看附件列表
     */
    @GetMapping("attachment-list")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo getAttachmentList(@RequestParam(required = false) Long boardId,
                                      @RequestParam(required = false) String username,
                                      @RequestParam(required = false) String filename,
                                      @RequestParam(required = false) Date from,
                                      @RequestParam(required = false) Date to,
                                      @RequestParam Long page,
                                      @RequestParam Long count) {
        count = PaginationUtil.clampPageSize(count);
        return ResultVo.success(attachmentServiceFacade.getAttachmentList(boardId, username, filename,
                from, to, page, count));
    }

    /**
     * 上传图片
     */
    @PostMapping("upload-image")
    @RequiresUser
    public ResultVo uploadImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResultVo.success(attachmentServiceFacade.uploadImage(multipartFile));
    }

    /**
     * 上传附件
     */
    @PostMapping("upload-attachment")
    @RequiresUser
    public ResultVo uploadAttachment(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResultVo.success(attachmentServiceFacade.uploadAttachment(multipartFile));
    }

    /**
     * 【管理】删除附件
     */
    @PostMapping("delete-attachment")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo deleteAttachment(@Valid @RequestBody IdListRequestVo requestVo) {
        attachmentServiceFacade.deleteAttachment(requestVo);
        return ResultVo.success(null);
    }

}
