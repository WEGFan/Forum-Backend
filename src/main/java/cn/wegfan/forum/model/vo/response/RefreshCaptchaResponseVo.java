package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefreshCaptchaResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图形验证码图片地址
     */
    private String url;

    /**
     * 随机字符串
     */
    private String verifyCodeRandom;

}
