package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 获取头像图片
     */
    @GetMapping("avatar/{filename}")
    public ResultVo getAvatarFile(@PathVariable String filename) {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 获取主题帖和回复帖的图片
     */
    @GetMapping("image/{filename}")
    public ResultVo getImageFile(@PathVariable String filename) {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

    /**
     * 获取附件文件
     */
    @GetMapping("attachment/{filename}")
    public ResultVo getAttachmentFile(@PathVariable String filename) {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

}
