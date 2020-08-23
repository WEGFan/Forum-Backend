package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdatePersonalUserInfoRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称 最大30字符
     */
    @NotNull
    @NotBlank
    @Length(min = 1, max = 30)
    private String nickname;

    /**
     * 性别，0-男，1-女，2-保密
     */
    @NotNull
    @Range(min = 0, max = 2)
    private Integer sex;

    /**
     * 个人签名 最大500字符
     */
    @NotNull
    @Length(max = 500)
    private String signature;

}
