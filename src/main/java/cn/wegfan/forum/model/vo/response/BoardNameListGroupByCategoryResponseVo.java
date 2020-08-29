package cn.wegfan.forum.model.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BoardNameListGroupByCategoryResponseVo implements Serializable {

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
     * 板块列表
     */
    private List<IdNameDescriptionResponseVo> boardList;

}
