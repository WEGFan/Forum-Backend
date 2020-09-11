package cn.wegfan.forum.controller;

import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.CaptchaServiceFacade;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/verifycode")
public class CaptchaController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CaptchaServiceFacade captchaServiceFacade;

    /**
     * 获取验证码
     */
    @GetMapping("refresh-code")
    public ResultVo refreshCaptcha() {
        return ResultVo.success(captchaServiceFacade.refreshCaptcha());
    }

    /**
     * 获取验证码图片
     */
    @GetMapping(value = "get-image", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getCaptchaImage(@RequestParam String random) throws IOException, FontFormatException {
        return captchaServiceFacade.getCaptchaImage(random);
    }

}
