package cn.wegfan.forum.model.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class IdRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull
    private Long id;

}
