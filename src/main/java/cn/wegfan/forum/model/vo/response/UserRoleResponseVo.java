package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRoleResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否有管理的板块
     */
    private Boolean boardAdmin;

    /**
     * 是否有管理的分区
     */
    private Boolean categoryAdmin;

    /**
     * 是否为超级版主
     */
    private Boolean superBoardAdmin;

    /**
     * 是否为管理员
     */
    private Boolean admin;

}
