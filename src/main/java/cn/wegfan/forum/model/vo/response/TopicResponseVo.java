package cn.wegfan.forum.model.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TopicResponseVo implements Serializable {

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
     * 内容
     */
    @JsonIgnore
    private String content;

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
     * 图片（最多三张）
     */
    private List<String> images;

    /**
     * 发布时间
     */
    private Date submitTime;

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

    /**
     * 浏览次数
     */
    private Long viewCount;

    /**
     * 回复总数
     */
    private Long replyCount;

    /**
     * 最后回复时间（没回复就是发帖时间 排序方便）
     */
    private Date lastReplyTime;

    /**
     * 最后回复者用户编号
     */
    private Long lastReplierUserId;

    /**
     * 最后回复者昵称
     */
    private String lastReplierNickname;

    /**
     * 是否置顶
     */
    private Boolean pinned;

    /**
     * 是否精华
     */
    private Boolean featured;

}
