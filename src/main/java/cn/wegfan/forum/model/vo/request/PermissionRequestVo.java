package cn.wegfan.forum.model.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PermissionRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 禁止访问
     */
    @NotNull
    private Boolean banVisit;

    /**
     * 禁止发表主题
     */
    @NotNull
    private Boolean banCreateTopic;

    /**
     * 禁止回复
     */
    @NotNull
    private Boolean banReply;

    /**
     * 禁止上传附件
     */
    @NotNull
    private Boolean banUploadAttachment;

    /**
     * 禁止下载附件
     */
    @NotNull
    private Boolean banDownloadAttachment;

}