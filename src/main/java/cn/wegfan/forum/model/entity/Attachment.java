package cn.wegfan.forum.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 附件
 */
@Data
@Alias("Attachment")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属主题编号
     */
    private Long topicId;

    /**
     * 所属板块编号
     */
    private Long boardId;

    /**
     * 原始文件名
     */
    private String filename;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 附件描述 200字符内
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
     * 上传者ip
     */
    private String uploaderIp;

    /**
     * 删除时间
     */
    private Date deleteTime;

}
