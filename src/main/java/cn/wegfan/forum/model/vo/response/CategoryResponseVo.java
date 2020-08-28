package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CategoryResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分区编号
     */
    private Long id;

    /**
     * 分区名称
     */
    private String name;

    /**
     * 分区描述
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
     * 分区版主
     */
    private List<IdNameResponseVo> categoryAdmin;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
