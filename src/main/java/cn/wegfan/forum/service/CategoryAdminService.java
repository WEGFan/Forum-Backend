package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.CategoryAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryAdminService extends IService<CategoryAdmin> {

    List<CategoryAdmin> listByCategoryId(Long categoryId);

    List<CategoryAdmin> listByUserId(Long userId);

    long countByUserId(Long userId);

}
