package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    List<Long> selectNotDeletedCategoryIdList();

    Category selectNotDeletedByName(String name);

    Category selectNotDeletedByCategoryId(Long categoryId);

    int deleteByCategoryId(Long categoryId);

    List<Category> selectNotDeletedAdminCategoryListByUserId(Long userId);

}
