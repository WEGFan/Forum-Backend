package cn.wegfan.forum.model.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 全站操作日志
 */
@Data
@Alias("OperationLog")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作编号
     */
    private Long id;

    /**
     * 操作者用户编号
     */
    private Long operatorUserId;

    /**
     * 操作者ip
     */
    private String operatorIp;

    /**
     * 操作对象类型 attachment/board/category/reply/topic/user
     */
    private String itemType;

    /**
     * 操作对象编号
     */
    private Long itemId;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 详细信息
     */
    private String detail;

    /**
     * 操作时间
     */
    private Date operateTime;

    public OperationLog() {
    }

}
