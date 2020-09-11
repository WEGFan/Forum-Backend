package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AttachmentRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件编号
     */
    @NotNull
    private Long id;

    /**
     * 附件描述
     */
    @NotNull
    @Length(max = 200)
    private String description;

}
