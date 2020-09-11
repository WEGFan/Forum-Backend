package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddReplyRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题帖编号
     */
    @NotNull
    private Long topicId;

    /**
     * 内容
     */
    @NotBlank
    @Length(max = 150000)
    private String content;

}
