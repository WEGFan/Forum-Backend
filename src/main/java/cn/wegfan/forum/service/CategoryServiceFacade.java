package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.entity.Category;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.CategoryRequestVo;
import cn.wegfan.forum.model.vo.response.CategoryResponseVo;
import cn.wegfan.forum.model.vo.response.IdNameResponseVo;
import cn.wegfan.forum.model.vo.response.PageResultVo;
import cn.wegfan.forum.util.BusinessException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    public PageResultVo<CategoryResponseVo> getAdminCategoryList(long pageIndex, long pageSize) {
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();

        Page<?> page = new Page<>(pageIndex, pageSize);
        Page<Category> pageResult = categoryService.listNotDeletedAdminCategoriesByPageAndUserId(page, userId);

        List<CategoryResponseVo> responseVoList = mapperFacade.mapAsList(pageResult.getRecords(), CategoryResponseVo.class);
        responseVoList.forEach(item -> {
            Long categoryId = item.getId();
            List<User> categoryAdminList = userService.listNotDeletedCategoryAdminsByCategoryId(categoryId);
            item.setCategoryAdmin(mapperFacade.mapAsList(categoryAdminList, IdNameResponseVo.class));
        });

        return new PageResultVo<>(responseVoList, pageResult);
    }

    public List<IdNameResponseVo> getAdminCategoryNameList() {
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();

        List<Category> categoryList = categoryService.listNotDeletedAdminCategoriesByUserId(userId);
        List<IdNameResponseVo> responseVoList = mapperFacade.mapAsList(categoryList, IdNameResponseVo.class);
        return responseVoList;
    }

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
