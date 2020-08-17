package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserLoginRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名 最大30字符 [A-Za-z0-9_]
     */
    @NotNull
    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z0-9_]*", message = "用户名只能包含大小写字母、数字、下划线")
    private String username;

    /**
     * 密码 6-20位
     */
    @NotNull
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;

    /**
     * 图形验证码 4位
     */
    @NotNull
    @NotBlank
    @Length(min = 4, max = 4, message = "验证码错误")
    private String verifyCode;

    /**
     * 图形验证码id
     */
    @NotNull
    @NotBlank
    private String verifyCodeRandom;

}
