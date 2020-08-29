package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdNameDescriptionResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

}
