package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserResetPasswordRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱
     */
    @NotNull
    @Email
    private String email;

    /**
     * 邮箱验证码
     */
    @NotNull
    @Length(min = 6, max = 6, message = "邮箱验证码错误")
    private String emailVerifyCode;

    /**
     * 新密码
     */
    @NotNull
    @Length(min = 6, max = 20)
    @Pattern(regexp = "[\\u0020-\\u007e]+", message = "密码只能包含大小写字母、数字、空格、符号")
    private String newPassword;

}
