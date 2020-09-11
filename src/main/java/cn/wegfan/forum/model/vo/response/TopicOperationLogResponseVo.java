package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data

public class TopicOperationLogResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题操作编号
     */
    private Long id;

    /**
     * 操作者用户编号
     */
    private Long operatorUserId;

    /**
     * 操作者用户名
     */
    private String operatorUsername;

    /**
     * 操作者ip
     */
    private String operatorIp;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作原因
     */
    private String reason;

    /**
     * 操作时间
     */
    private Date operateTime;

}
