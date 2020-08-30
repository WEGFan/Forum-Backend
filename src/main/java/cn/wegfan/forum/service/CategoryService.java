package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.CategoryListSortEnum;
import cn.wegfan.forum.model.entity.Category;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<Long> listNotDeletedCategoryIds();

    Page<Category> listNotDeletedCategoriesByPage(Page<?> page, CategoryListSortEnum sortEnum);

    List<Category> listNotDeletedCategories(CategoryListSortEnum sortEnum);

    Category getNotDeletedCategoryByCategoryId(Long categoryId);

    Category getNotDeletedCategoryByName(String name);

    int addCategory(Category category);

    int updateCategory(Category category);

    int deleteCategoryByCategoryId(Long categoryId);

    List<Category> listNotDeletedAllAdminCategoriesByUserId(Long userId, CategoryListSortEnum sortEnum);

    Page<Category> listNotDeletedAllAdminCategoriesByPageAndUserId(Page<?> page, Long userId, CategoryListSortEnum sortEnum);

    List<Category> listNotDeletedAdminCategoriesByUserId(Long userId);

}
