package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<Long> listNotDeletedCategoryIds();

    Category getNotDeletedCategoryByCategoryId(Long categoryId);

    Category getNotDeletedCategoryByName(String name);

    int addCategory(Category category);

    int updateCategory(Category category);

    int deleteCategoryByCategoryId(Long categoryId);

}
