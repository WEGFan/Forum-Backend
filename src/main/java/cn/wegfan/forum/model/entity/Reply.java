package cn.wegfan.forum.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复
 */
@Data
@Alias("Reply")
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回复编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属主题编号
     */
    private Long topicId;

    /**
     * 回复内容 150000字符内
     */
    private String content;

    /**
     * 短内容 纯文字
     */
    private String shortContent;

    /**
     * 回复时间
     */
    private Date replyTime;

    /**
     * 回复者用户编号
     */
    private Long replierUserId;

    /**
     * 回复者ip
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
     * 最后编辑者ip
     */
    private String editorIp;

    /**
     * 删除时间
     */
    private Date deleteTime;

}
