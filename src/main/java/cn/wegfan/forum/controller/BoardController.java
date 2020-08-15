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
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 【管理】查看所有板块
     */
    @GetMapping("board-list")
    public ResultVo getBoardList(@RequestParam Integer page,
                                 @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】查看板块名称列表
     */
    @GetMapping("board-name-list")
    public ResultVo getBoardNameList() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】创建板块
     */
    @PostMapping("add-board")
    public ResultVo addBoard() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】编辑板块
     */
    @PostMapping("update-board")
    public ResultVo updateBoard() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】删除板块
     */
    @PostMapping("delete-board")
    public ResultVo deleteBoard() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

}
