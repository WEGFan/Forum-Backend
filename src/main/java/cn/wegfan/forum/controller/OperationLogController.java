package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/log")
public class OperationLogController {

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 【管理】查看全站操作记录列表
     */
    @GetMapping("operation-log")
    public ResultVo getOperationLogList(@RequestParam(required = false) String username,
                                        @RequestParam Integer page,
                                        @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NOT_IMPLEMENTED);
    }

}
