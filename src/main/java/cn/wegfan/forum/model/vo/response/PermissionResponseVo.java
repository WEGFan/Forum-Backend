package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
