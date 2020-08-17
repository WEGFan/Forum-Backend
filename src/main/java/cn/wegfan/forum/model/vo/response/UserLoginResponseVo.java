package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户名 最大30字符
     */
    private String username;

    /**
     * 昵称 最大30字符
     */
    private String nickname;

    /**
     * 头像文件地址
     */
    private String avatarPath;

    /**
     * 用户的特殊角色
     */
    private UserRoleResponseVo permission;

}
