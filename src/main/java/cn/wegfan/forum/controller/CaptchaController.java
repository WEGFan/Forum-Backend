package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/verifycode")
public class CaptchaController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 获取验证码
     */
    @GetMapping("refresh-code")
    public ResultVo refreshCaptcha() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 获取验证码图片
     */
    @GetMapping("get-image")
    public ResultVo getCaptchaImage(@RequestParam String random) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

}
