package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 是否为超级版主
     */
    private Boolean superBoardAdmin;

    /**
     * 是否为管理员
     */
    private Boolean admin;

    /**
     * 头像文件地址
     */
    private String avatarPath;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否已激活邮箱
     */
    private Boolean emailVerified;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 个人签名
     */
    private String signature;

    /**
     * 主题帖总数
     */
    private Long topicCount;

    /**
     * 回复总数
     */
    private Long replyCount;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 管理的板块
     */
    private List<BoardResponseVo> boardAdmin;

    /**
     * 管理的分区
     */
    private List<CategoryResponseVo> categoryAdmin;

    /**
     * 论坛权限
     */
    private PermissionResponseVo forumPermission;

    /**
     * 板块权限
     */
    private List<IdNamePermissionResponseVo> boardPermission;

}
