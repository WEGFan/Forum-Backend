package cn.wegfan.forum.model.entity;

import cn.wegfan.forum.constant.SexEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 */
@Data
@Alias("User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名 最大30字符
     */
    private String username;

    /**
     * bcrypt加密后的密码
     */
    private String password;

    /**
     * 昵称 最大30字符
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否已激活邮箱
     */
    private Boolean emailVerified;

    /**
     * 性别 0-男 1-女 2-保密
     */
    private SexEnum sex;

    /**
     * 个人签名 最大500字符
     */
    private String signature;

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
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    @TableField(exist = false)
    private List<Topic> topicList;

}
