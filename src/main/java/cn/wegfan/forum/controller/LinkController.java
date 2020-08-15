package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/link")
public class LinkController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 查看所有友情链接
     */
    @GetMapping("link-list")
    public ResultVo getCategoryList() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】添加友情链接
     */
    @PostMapping("add-link")
    public ResultVo addLink() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】编辑友情链接
     */
    @PostMapping("update-link")
    public ResultVo updateLink() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】删除友情链接
     */
    @PostMapping("delete-link")
    public ResultVo deleteLink() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

}
