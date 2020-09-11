package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttachmentResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件编号
     */
    private Long id;

    /**
     * 所属主题编号
     */
    private Long topicId;

    /**
     * 所属主题标题
     */
    private String topicTitle;

    /**
     * 所属板块编号
     */
    private Long boardId;

    /**
     * 所属板块名称
     */
    private String boardName;

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

    /**
     * 上传者用户编号
     */
    private Long uploaderUserId;

    /**
     * 上传者用户名
     */
    private String uploaderUsername;

    /**
     * 上传者ip
     */
    private String uploaderIp;

}
