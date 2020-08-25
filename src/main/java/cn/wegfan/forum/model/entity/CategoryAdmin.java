package cn.wegfan.forum.model.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 分区管理
 */
@Data
@Alias("CategoryAdmin")
public class CategoryAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 分区编号
     */
    private Long categoryId;

    public CategoryAdmin(Long userId, Long categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

}
