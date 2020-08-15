package cn.wegfan.forum.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题操作日志
 */
@Data
@Alias("TopicOperationLog")
public class TopicOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题操作编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主题编号
     */
    private Long topicId;

    /**
     * 操作者用户编号
     */
    private Long operatorUserId;

    /**
     * 操作者ip
     */
    private String operatorIp;

    /**
     * 操作原因
     */
    private String reason;

    /**
     * 操作时间
     */
    private Date operateTime;

}
