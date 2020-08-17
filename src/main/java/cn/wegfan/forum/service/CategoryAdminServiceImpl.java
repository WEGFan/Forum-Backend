package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.CategoryAdminMapper;
import cn.wegfan.forum.model.entity.CategoryAdmin;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
