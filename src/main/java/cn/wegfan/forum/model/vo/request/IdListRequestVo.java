package cn.wegfan.forum.model.vo.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class IdListRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号列表
     */
    @NotNull
    @NotEmpty
    private List<Long> idList;

}
