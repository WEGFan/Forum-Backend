package cn.wegfan.forum.service;

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
    public Page<Category> listNotDeletedCategoriesByPage(Page<?> page) {
        return categoryMapper.selectNotDeletedCategoryListByPage(page);
    }

    @Override
    public List<Category> listNotDeletedCategories() {
        return categoryMapper.selectNotDeletedCategoryListByPage(Constant.UNPAGED_PAGE).getRecords();
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
    public List<Category> listNotDeletedAdminCategoriesByUserId(Long userId) {
        return listNotDeletedAdminCategoriesByPageAndUserId(Constant.UNPAGED_PAGE, userId).getRecords();
    }

    @Override
    public Page<Category> listNotDeletedAdminCategoriesByPageAndUserId(Page<?> page, Long userId) {
        User user = userMapper.selectNotDeletedByUserId(userId);
        // 如果管理员或超级版主则直接获取所有分区
        if (user.getAdmin() || user.getSuperBoardAdmin()) {
            return categoryMapper.selectNotDeletedCategoryListByPage(page);
        } else {
            return categoryMapper.selectNotDeletedAdminCategoryListByUserId(page, userId);
        }
    }

}
