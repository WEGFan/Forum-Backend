package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TopicAttachmentResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件编号
     */
    private Long id;

    /**
     * 原始文件名
     */
    private String filename;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 附件描述
     */
    private String description;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 下载次数
     */
    private Long downloadCount;

    /**
     * 上传（创建）时间
     */
    private Date uploadTime;

}
