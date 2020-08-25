package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.BoardAdminMapper;
import cn.wegfan.forum.model.entity.BoardAdmin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public boolean batchAddBoardAdminByUserId(Long userId, Set<Long> boardIdList) {
        List<BoardAdmin> boardAdminList = boardIdList.stream()
                .map(i -> new BoardAdmin(userId, i))
                .collect(Collectors.toList());
        return saveBatch(boardAdminList);
    }

    @Override
    public boolean batchDeleteBoardAdminByUserId(Long userId, Set<Long> boardIdList) {
        if (boardIdList.isEmpty()) {
            return false;
        }
        return remove(new QueryWrapper<BoardAdmin>()
                .lambda()
                .eq(BoardAdmin::getUserId, userId)
                .in(BoardAdmin::getBoardId, boardIdList));
    }

    @Override
    public int deleteBoardAdminByBoardId(Long boardId) {
        return boardAdminMapper.deleteByBoardId(boardId);
    }

    @Override
    public Set<Long> listUserIdByBoardId(Long boardId) {
        return boardAdminMapper.selectUserIdSetByBoardId(boardId);
    }

    @Override
    public Set<Long> listBoardIdByUserId(Long userId) {
        return boardAdminMapper.selectBoardIdSetByUserId(userId);
    }

}
