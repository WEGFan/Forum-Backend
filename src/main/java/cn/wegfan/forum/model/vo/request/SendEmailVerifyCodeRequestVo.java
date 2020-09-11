package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SendEmailVerifyCodeRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱
     */
    @Email
    @NotBlank
    private String email;

    /**
     * 图形验证码 4位
     */
    @NotNull
    @Length(min = 4, max = 4, message = "验证码错误")
    private String verifyCode;

    /**
     * 图形验证码id
     */
    @NotNull
    @NotBlank
    private String verifyCodeRandom;

}
