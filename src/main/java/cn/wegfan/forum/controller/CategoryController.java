package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.CategoryListSortEnum;
import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.model.vo.request.CategoryRequestVo;
import cn.wegfan.forum.model.vo.request.IdRequestVo;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.CategoryServiceFacade;
import cn.wegfan.forum.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private CategoryServiceFacade categoryServiceFacade;

    /**
     * 【管理】查看所有分区
     */
    @GetMapping("category-list")
    @RequiresUser
    public ResultVo getCategoryList(@RequestParam(required = false) String sort,
                                    @RequestParam Long page,
                                    @RequestParam Long count) {
        count = PaginationUtil.clampPageSize(count);
        CategoryListSortEnum sortEnum = CategoryListSortEnum.fromValue(sort, CategoryListSortEnum.ID);
        return ResultVo.success(categoryServiceFacade.getAdminCategoryList(sortEnum, page, count));
    }

    /**
     * 【管理】查看分区名称列表
     */
    @GetMapping("category-name-list")
    @RequiresUser
    public ResultVo getCategoryNameList() {
        return ResultVo.success(categoryServiceFacade.getAdminCategoryNameList());
    }

    /**
     * 【管理】创建分区
     */
    @PostMapping("add-category")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo addCategory(@Valid @RequestBody CategoryRequestVo requestVo) {
        categoryServiceFacade.addCategory(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】编辑分区
     */
    @PostMapping("update-category")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo updateCategory(@Valid @RequestBody CategoryRequestVo requestVo) {
        categoryServiceFacade.updateCategory(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】删除分区
     */
    @PostMapping("delete-category")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo deleteCategory(@Valid @RequestBody IdRequestVo requestVo) {
        categoryServiceFacade.deleteCategory(requestVo.getId());
        return ResultVo.success(null);
    }

}
