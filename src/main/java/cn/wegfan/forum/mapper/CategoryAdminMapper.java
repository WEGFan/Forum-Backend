package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.CategoryAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryAdminMapper extends BaseMapper<CategoryAdmin> {

    List<CategoryAdmin> selectListByCategoryId(Long categoryId);

    List<CategoryAdmin> selectListByUserId(Long userId);

    long countByUserId(Long userId);

}
