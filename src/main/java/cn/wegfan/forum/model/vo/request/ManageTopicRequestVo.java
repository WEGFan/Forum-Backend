package cn.wegfan.forum.model.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ManageTopicRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题帖编号列表
     */
    @NotNull
    @NotEmpty
    private List<Long> idList;

    /**
     * 操作
     */
    @NotBlank
    private String action;

    /**
     * 操作原因
     */
    @NotBlank
    @Length(max = 200)
    private String reason;

}
