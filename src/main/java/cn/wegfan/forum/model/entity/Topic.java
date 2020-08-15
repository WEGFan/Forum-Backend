package cn.wegfan.forum.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题
 */
@Data
@Alias("Topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所在分区编号
     */
    private Long categoryId;

    /**
     * 所在板块编号
     */
    private Long boardId;

    /**
     * 主题类型 0-普通主题 1-公告
     */
    private Integer type;

    /**
     * 标题 120字符内
     */
    private String title;

    /**
     * 内容 150000字符内
     */
    private String content;

    /**
     * 短内容 纯文字
     */
    private String shortContent;

    /**
     * 发布时间
     */
    private Date submitTime;

    /**
     * 发布者用户编号
     */
    private Long submitterUserId;

    /**
     * 发布者ip
     */
    private String submitterIp;

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
     * 最后编辑者ip
     */
    private String editorIp;

    /**
     * 删除时间
     */
    private Date deleteTime;

}
