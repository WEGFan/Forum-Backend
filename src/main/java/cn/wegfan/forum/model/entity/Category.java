package cn.wegfan.forum.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 分区
 */
@Data
@Alias("Category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分区编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分区名称 最大30字符
     */
    private String name;

    /**
     * 分区描述 最大200字符
     */
    private String description;

    /**
     * 是否显示
     */
    private Boolean visible;

    /**
     * 顺序 小的先显示 1-100
     */
    private Integer order;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

}
