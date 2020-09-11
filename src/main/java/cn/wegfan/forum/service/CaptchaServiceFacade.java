package cn.wegfan.forum.service;

import cn.hutool.core.codec.Base64;
import cn.wegfan.forum.model.vo.response.RefreshCaptchaResponseVo;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CaptchaServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CaptchaService captchaService;

    public RefreshCaptchaResponseVo refreshCaptcha() {
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        String random = Base64.encodeUrlSafe(generator.nextBytes(12).getBytes());

        RefreshCaptchaResponseVo responseVo = new RefreshCaptchaResponseVo();
        responseVo.setVerifyCodeRandom(random);
        responseVo.setUrl("/api/verifycode/get-image?random=" + random);
        return responseVo;
    }

    public byte[] getCaptchaImage(String random) throws IOException, FontFormatException {
        Captcha captcha = captchaService.getCaptcha();
        String captchaText = captcha.text();
        log.debug("random: {}, captcha text：{}", random, captchaText);
        captchaService.storeCaptchaToRedis(random, captchaText);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        captcha.out(outputStream);
        return outputStream.toByteArray();
    }

}
