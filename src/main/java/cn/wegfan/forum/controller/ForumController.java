package cn.wegfan.forum.controller;

import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.BoardServiceFacade;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private BoardServiceFacade boardServiceFacade;

    /**
     * 论坛主页获取分区和板块信息
     */
    @GetMapping("home-board-list")
    public ResultVo getForumHomeBoardList() {
        return ResultVo.success(boardServiceFacade.getHomepageBoardList());
    }

}
