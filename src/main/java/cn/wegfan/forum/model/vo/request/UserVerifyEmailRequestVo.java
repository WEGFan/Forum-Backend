package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserVerifyEmailRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱验证码
     */
    @NotNull
    @Length(min = 6, max = 6, message = "邮箱验证码错误")
    private String emailVerifyCode;

}
