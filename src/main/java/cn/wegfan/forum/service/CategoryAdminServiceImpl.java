package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.CategoryAdminMapper;
import cn.wegfan.forum.model.entity.CategoryAdmin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = "categoryAdmin")
@Transactional(rollbackFor = Exception.class)
public class CategoryAdminServiceImpl extends ServiceImpl<CategoryAdminMapper, CategoryAdmin> implements CategoryAdminService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    @Override
    public List<CategoryAdmin> listByCategoryId(Long categoryId) {
        return categoryAdminMapper.selectListByCategoryId(categoryId);
    }

    @Override
    public List<CategoryAdmin> listByUserId(Long userId) {
        return categoryAdminMapper.selectListByUserId(userId);
    }

    @Override
    public long countByUserId(Long userId) {
        return categoryAdminMapper.countByUserId(userId);
    }

    @Override
    public boolean batchAddCategoryAdminByUserId(Long userId, Set<Long> categoryIdList) {
        List<CategoryAdmin> categoryAdminList = categoryIdList.stream()
                .map(i -> new CategoryAdmin(userId, i))
                .collect(Collectors.toList());
        return saveBatch(categoryAdminList);
    }

    @Override
    public boolean batchDeleteCategoryAdminByUserId(Long userId, Set<Long> categoryIdList) {
        if (categoryIdList.isEmpty()) {
            return false;
        }
        return remove(new QueryWrapper<CategoryAdmin>()
                .lambda()
                .eq(CategoryAdmin::getUserId, userId)
                .in(CategoryAdmin::getCategoryId, categoryIdList));
    }

    @Override
    public int deleteCategoryAdminByCategoryId(Long categoryId) {
        return categoryAdminMapper.deleteByCategoryId(categoryId);
    }

    @Override
    public Set<Long> listUserIdByCategoryId(Long categoryId) {
        return categoryAdminMapper.selectUserIdSetByCategoryId(categoryId);
    }

    @Override
    public Set<Long> listCategoryIdByUserId(Long userId) {
        return categoryAdminMapper.selectCategoryIdSetByUserId(userId);
    }

}
