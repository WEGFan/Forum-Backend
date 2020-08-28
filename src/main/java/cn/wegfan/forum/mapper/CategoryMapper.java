package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    List<Long> selectNotDeletedCategoryIdList();

    Page<Category> selectNotDeletedCategoryListByPage(Page<?> page);

    Category selectNotDeletedByName(String name);

    Category selectNotDeletedByCategoryId(Long categoryId);

    int deleteByCategoryId(Long categoryId);

    Page<Category> selectNotDeletedAdminCategoryListByUserId(Page<?> page, Long userId);

}
