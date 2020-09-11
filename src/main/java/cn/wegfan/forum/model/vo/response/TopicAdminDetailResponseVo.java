package cn.wegfan.forum.model.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TopicAdminDetailResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题编号
     */
    private Long id;

    /**
     * 主题类型 0-普通主题 1-公告
     */
    private Integer type;

    /**
     * 标题
     */
    private String title;

    /**
     * 短内容
     */
    private String shortContent;

    /**
     * 纯文字内容
     */
    @JsonIgnore
    private String contentText;

    /**
     * 发布时间
     */
    private Date submitTime;

    /**
     * 发帖人用户编号
     */
    private Long submitterUserId;

    /**
     * 发帖人用户名
     */
    private String submitterUsername;

    /**
     * 发帖人昵称
     */
    private String submitterNickname;

    /**
     * 发帖人IP地址
     */
    private String submitterIp;

    /**
     * 发帖人头像地址
     */
    private String submitterAvatarPath;

    /**
     * 所在板块编号
     */
    private Long boardId;

    /**
     * 所在板块名称
     */
    private String boardName;

    /**
     * 所在分区编号
     */
    private Long categoryId;

    /**
     * 所在分区名称
     */
    private String categoryName;

    /**
     * 浏览次数
     */
    private Long viewCount;

    /**
     * 回复总数
     */
    private Long replyCount;

    /**
     * 是否置顶
     */
    private Boolean pinned;

    /**
     * 是否精华
     */
    private Boolean featured;

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
     * 最后编辑者IP地址
     */
    private String editorIp;

}
