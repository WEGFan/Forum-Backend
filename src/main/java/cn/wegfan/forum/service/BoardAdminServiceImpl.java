package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.BoardAdminMapper;
import cn.wegfan.forum.model.entity.BoardAdmin;
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
@CacheConfig(cacheNames = "boardAdmin")
@Transactional(rollbackFor = Exception.class)
public class BoardAdminServiceImpl extends ServiceImpl<BoardAdminMapper, BoardAdmin> implements BoardAdminService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private BoardAdminMapper boardAdminMapper;

    @Override
    public List<BoardAdmin> listByBoardId(Long boardId) {
        return boardAdminMapper.selectListByBoardId(boardId);
    }

    @Override
    public List<BoardAdmin> listByUserId(Long userId) {
        return boardAdminMapper.selectListByUserId(userId);
    }

    @Override
    public long countByUserId(Long userId) {
        return boardAdminMapper.countByUserId(userId);
    }

}
