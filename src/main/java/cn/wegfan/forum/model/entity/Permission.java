package cn.wegfan.forum.model.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 权限
 */
@Data
@Alias("Permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 板块编号
     */
    private Long boardId;

    /**
     * 禁止访问
     */
    private Boolean banVisit;

    /**
     * 禁止发表主题
     */
    private Boolean banCreateTopic;

    /**
     * 禁止回复
     */
    private Boolean banReply;

    /**
     * 禁止上传附件
     */
    private Boolean banUploadAttachment;

    /**
     * 禁止下载附件
     */
    private Boolean banDownloadAttachment;

}
