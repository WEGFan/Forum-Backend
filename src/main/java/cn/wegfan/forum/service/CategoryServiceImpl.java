package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.CategoryMapper;
import cn.wegfan.forum.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@CacheConfig(cacheNames = "category")
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category getNotDeletedCategoryByCategoryId(Long categoryId) {
        return categoryMapper.selectNotDeletedByCategoryId(categoryId);
    }

    @Override
    public Category getNotDeletedCategoryByName(String name) {
        return categoryMapper.selectNotDeletedByName(name);
    }

    @Override
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
    public int deleteCategoryByCategoryId(Long categoryId) {
        int result = categoryMapper.deleteByCategoryId(categoryId);
        return result;
    }

}
