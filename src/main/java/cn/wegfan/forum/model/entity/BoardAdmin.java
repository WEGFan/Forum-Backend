package cn.wegfan.forum.model.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 板块管理
 */
@Data
@Alias("BoardAdmin")
public class BoardAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 板块编号
     */
    private Long boardId;

}
