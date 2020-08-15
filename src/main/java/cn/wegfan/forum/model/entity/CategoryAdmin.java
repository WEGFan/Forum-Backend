package cn.wegfan.forum.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 分区编号
     */
    private Long categoryId;

}
