package cn.wegfan.forum.service;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;

@Service
@Slf4j
public class CaptchaService {

    private final static String CAPTCHA_PREFIX = "forum:captcha:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getRedisKey(String key) {
        return CAPTCHA_PREFIX + key;
    }

    public Captcha getCaptcha() throws IOException, FontFormatException {
        SpecCaptcha captcha = new SpecCaptcha(150, 50, 4);
        captcha.setFont(Captcha.FONT_2, 42);
        captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
        return captcha;
    }

    public void storeCaptchaToRedis(String random, String captchaText) {
        redisTemplate.opsForValue().set(getRedisKey(random), captchaText, Duration.ofMinutes(5));
    }

    public boolean checkCaptcha(String random, String captcha) {
        String redisKey = getRedisKey(random);
        String correctCaptcha = (String)redisTemplate.opsForValue().get(redisKey);
        return captcha.equalsIgnoreCase(correctCaptcha);
    }

    public void deleteCaptcha(String random) {
        String redisKey = getRedisKey(random);
        redisTemplate.delete(redisKey);
    }

    public String getEmailVerifyCode() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(new char[] {'A', 'Z'}, new char[] {'0', '9'})
                .build();
        return generator.generate(6);
    }

    public void storeEmailVerifyCodeToRedis(String email, String verifyCode) {
        redisTemplate.opsForValue().set(getRedisKey(email), verifyCode, Duration.ofMinutes(5));
    }

    public boolean checkEmailVerifyCode(String email, String verifyCode) {
        String redisKey = getRedisKey(email);
        String correctCode = (String)redisTemplate.opsForValue().get(redisKey);
        return verifyCode.equalsIgnoreCase(correctCode);
    }

    public void deleteEmailVerifyCode(String email) {
        String redisKey = getRedisKey(email);
        redisTemplate.delete(redisKey);
    }

}
