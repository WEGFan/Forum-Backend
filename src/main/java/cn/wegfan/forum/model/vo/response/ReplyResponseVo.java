package cn.wegfan.forum.model.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReplyResponseVo implements Serializable {

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
     * 纯文字内容
     */
    @JsonIgnore
    private String contentText;

    /**
     * 短内容
     */
    private String shortContent;

    /**
     * 所属主题帖编号
     */
    private Long topicId;

    /**
     * 所属主题帖标题
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
     * 回复者用户名
     */
    private String replierUsername;

    /**
     * 回复者昵称
     */
    private String replierNickname;

    /**
     * 回复者IP
     */
    private String replierIp;

    /**
     * 最后编辑时间
     */
    private Date editTime;

    /**
     * 最后编辑者用户编号
     */
    private Long editorUserId;

    /**
     * 最后编辑者用户名
     */
    private String editorUsername;

    /**
     * 最后编辑者昵称
     */
    private String editorNickname;

    /**
     * 最后编辑者IP
     */
    private String editorIp;

}
