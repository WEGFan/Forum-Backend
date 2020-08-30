package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.CategoryListSortEnum;
import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.mapper.CategoryMapper;
import cn.wegfan.forum.mapper.UserMapper;
import cn.wegfan.forum.model.entity.Category;
import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "category")
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(key = "'notDeletedIdList'")
    public List<Long> listNotDeletedCategoryIds() {
        return categoryMapper.selectNotDeletedCategoryIdList();
    }

    @Override
    public Page<Category> listNotDeletedCategoriesByPage(Page<?> page, CategoryListSortEnum sortEnum) {
        return categoryMapper.selectNotDeletedCategoryListByPage(page, sortEnum.getOrderBySql());
    }

    @Override
    public List<Category> listNotDeletedCategories(CategoryListSortEnum sortEnum) {
        return categoryMapper.selectNotDeletedCategoryListByPage(Constant.UNPAGED_PAGE, sortEnum.getOrderBySql()).getRecords();
    }

    @Override
    public Category getNotDeletedCategoryByCategoryId(Long categoryId) {
        return categoryMapper.selectNotDeletedByCategoryId(categoryId);
    }

    @Override
    public Category getNotDeletedCategoryByName(String name) {
        return categoryMapper.selectNotDeletedByName(name);
    }

    @Override
    @CacheEvict(key = "'notDeletedIdList'")
    public int addCategory(Category category) {
        category.setId(null);
        Date now = new Date();
        category.setCreateTime(now);
        category.setUpdateTime(now);
        int result = categoryMapper.insert(category);
        return result;
    }

    @Override
    public int updateCategory(Category category) {
        Date now = new Date();
        category.setUpdateTime(now);
        int result = categoryMapper.updateById(category);
        return result;
    }

    @Override
    @CacheEvict(key = "'notDeletedIdList'")
    public int deleteCategoryByCategoryId(Long categoryId) {
        int result = categoryMapper.deleteByCategoryId(categoryId);
        return result;
    }

    @Override
    public List<Category> listNotDeletedAllAdminCategoriesByUserId(Long userId, CategoryListSortEnum sortEnum) {
        return listNotDeletedAllAdminCategoriesByPageAndUserId(Constant.UNPAGED_PAGE, userId, sortEnum).getRecords();
    }

    @Override
    public Page<Category> listNotDeletedAllAdminCategoriesByPageAndUserId(Page<?> page, Long userId, CategoryListSortEnum sortEnum) {
        User user = userMapper.selectNotDeletedByUserId(userId);
        // 如果管理员或超级版主则直接获取所有分区
        if (user.getAdmin() || user.getSuperBoardAdmin()) {
            return categoryMapper.selectNotDeletedCategoryListByPage(page, sortEnum.getOrderBySql());
        } else {
            return categoryMapper.selectNotDeletedAdminCategoryListByPageAndUserId(page, userId, sortEnum.getOrderBySql());
        }
    }

    @Override
    public List<Category> listNotDeletedAdminCategoriesByUserId(Long userId) {
        return categoryMapper.selectNotDeletedAdminCategoryListByPageAndUserId(Constant.UNPAGED_PAGE, userId,
                CategoryListSortEnum.ID.getOrderBySql()).getRecords();
    }

}
