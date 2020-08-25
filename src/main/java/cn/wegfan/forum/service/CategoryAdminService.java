package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.CategoryAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface CategoryAdminService extends IService<CategoryAdmin> {

    List<CategoryAdmin> listByCategoryId(Long categoryId);

    List<CategoryAdmin> listByUserId(Long userId);

    long countByUserId(Long userId);

    boolean batchAddCategoryAdminByUserId(Long userId, Set<Long> categoryIdList);

    boolean batchDeleteCategoryAdminByUserId(Long userId, Set<Long> categoryIdList);

    int deleteCategoryAdminByCategoryId(Long categoryId);

    Set<Long> listUserIdByCategoryId(Long categoryId);

    Set<Long> listCategoryIdByUserId(Long userId);

}
