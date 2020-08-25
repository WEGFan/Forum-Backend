package cn.wegfan.forum.model.vo.request;

import cn.wegfan.forum.config.validator.AllMin;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UpdateBoardAdminRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @NotNull
    private Long userId;

    /**
     * 板块编号列表
     */
    @NotNull
    @AllMin(1)
    private List<Long> boardIdList;

}
