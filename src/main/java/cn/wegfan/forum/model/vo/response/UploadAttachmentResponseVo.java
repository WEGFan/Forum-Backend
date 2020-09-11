package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadAttachmentResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件编号
     */
    private Long id;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 下载链接
     */
    private String downloadUrl;

    /**
     * 附件描述
     */
    private String description;

}
