package cn.wegfan.forum.model.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserReplyResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回复编号
     */
    private Long id;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复纯文字内容
     */
    @JsonIgnore
    private String contentText;

    /**
     * 回复短内容
     */
    private String shortContent;

    /**
     * 主题帖编号
     */
    private Long topicId;

    /**
     * 主题帖标题
     */
    private String topicTitle;

    /**
     * 回复时间
     */
    private Date replyTime;

    /**
     * 回复者用户编号
     */
    private Long replierUserId;

    /**
     * 回复者昵称
     */
    private String replierNickname;

    /**
     * 发帖人用户编号
     */
    private Long submitterUserId;

    /**
     * 发帖人昵称
     */
    private String submitterNickname;

    /**
     * 发帖人头像地址
     */
    private String submitterAvatarPath;

}
