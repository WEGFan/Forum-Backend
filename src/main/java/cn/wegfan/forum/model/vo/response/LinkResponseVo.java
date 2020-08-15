package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Date;

@Data
public class LinkResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 友情链接编号
     */
    private Long id;

    /**
     * 名称 最大30字符
     */
    private String name;

    /**
     * 网址
     */
    private String url;

    /**
     * 图标地址
     */
    @Nullable
    private String iconUrl;

    /**
     * 顺序 小的先显示 1-100
     */
    private Integer order;

    /**
     * 描述 最大200字符
     */
    private String description;

    /**
     * 创建时间
     */
    @Nullable
    private Date createTime;

    /**
     * 更新时间
     */
    @Nullable
    private Date updateTime;

}

