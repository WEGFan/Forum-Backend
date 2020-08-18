package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    Category selectNotDeletedByName(String name);

    Category selectNotDeletedByCategoryId(Long categoryId);

    int deleteByCategoryId(Long categoryId);

}
