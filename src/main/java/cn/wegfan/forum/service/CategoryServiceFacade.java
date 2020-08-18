package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.entity.Category;
import cn.wegfan.forum.model.vo.request.CategoryRequestVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CategoryService categoryService;

    public int addCategory(CategoryRequestVo requestVo) {
        Category sameNameCategory = categoryService.getNotDeletedCategoryByName(requestVo.getName());
        if (sameNameCategory != null) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_CATEGORY_NAME);
        }
        Category category = mapperFacade.map(requestVo, Category.class);
        return categoryService.addCategory(category);
    }

    public int updateCategory(CategoryRequestVo requestVo) {
        Category category = categoryService.getNotDeletedCategoryByCategoryId(requestVo.getId());
        if (category == null) {
            throw new BusinessException(BusinessErrorEnum.CATEGORY_NOT_FOUND);
        }
        Category sameNameCategory = categoryService.getNotDeletedCategoryByName(requestVo.getName());
        if (sameNameCategory != null && !sameNameCategory.getId().equals(category.getId())) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_CATEGORY_NAME);
        }

        mapperFacade.map(requestVo, category);
        return categoryService.updateCategory(category);
    }

    public int deleteCategory(Long categoryId) {
        // TODO: 删除分区内的板块、帖子、回复、分区版主
        Category category = categoryService.getNotDeletedCategoryByCategoryId(categoryId);
        if (category == null) {
            throw new BusinessException(BusinessErrorEnum.CATEGORY_NOT_FOUND);
        }
        return categoryService.deleteCategoryByCategoryId(categoryId);
    }

}
