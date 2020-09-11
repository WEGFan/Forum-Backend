package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TopicReplyResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回复编号
     */
    private Long id;

    /**
     * 主题帖编号
     */
    private Long topicId;

    /**
     * 主题帖标题
     */
    private String topicTitle;

    /**
     * 回复内容
     */
    private String content;

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
     * 回复者头像地址
     */
    private String replierAvatarPath;

    /**
     * 最后编辑时间
     */
    private Date editTime;

    /**
     * 最后编辑者用户编号
     */
    private Long editorUserId;

    /**
     * 最后编辑者昵称
     */
    private String editorNickname;

}
