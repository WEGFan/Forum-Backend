package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.model.vo.request.IdRequestVo;
import cn.wegfan.forum.model.vo.request.LinkRequestVo;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.LinkServiceFacade;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/link")
public class LinkController {

    @Autowired
    private LinkServiceFacade linkServiceFacade;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 查看所有友情链接
     */
    @GetMapping("link-list")
    public ResultVo getCategoryList() {
        return ResultVo.success(linkServiceFacade.getLinkList());
    }

    /**
     * 【管理】添加友情链接
     */
    @PostMapping("add-link")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo addLink(@Valid @RequestBody LinkRequestVo requestVo) {
        linkServiceFacade.addLink(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】编辑友情链接
     */
    @PostMapping("update-link")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo updateLink(@Valid @RequestBody LinkRequestVo requestVo) {
        linkServiceFacade.updateLink(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】删除友情链接
     */
    @PostMapping("delete-link")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo deleteLink(@Valid @RequestBody IdRequestVo requestVo) {
        linkServiceFacade.deleteLink(requestVo.getId());
        return ResultVo.success(null);
    }

}
