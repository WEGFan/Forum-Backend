package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 【管理】查看所有分区
     */
    @GetMapping("category-list")
    public ResultVo getCategoryList(@RequestParam Integer page,
                                    @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】查看分区名称列表
     */
    @GetMapping("category-name-list")
    public ResultVo getCategoryNameList() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】创建分区
     */
    @PostMapping("add-category")
    public ResultVo addCategory() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】编辑分区
     */
    @PostMapping("update-category")
    public ResultVo updateCategory() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】删除分区
     */
    @PostMapping("delete-category")
    public ResultVo deleteCategory() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

}
