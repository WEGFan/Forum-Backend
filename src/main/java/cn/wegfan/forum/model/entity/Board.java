package cn.wegfan.forum.model.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 板块
 */
@Data
@Alias("Board")
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 板块编号
     */
    private Long id;

    /**
     * 所属分区编号
     */
    private Long categoryId;

    /**
     * 板块名称 最大30字符
     */
    private String name;

    /**
     * 板块描述 最大200字符
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

