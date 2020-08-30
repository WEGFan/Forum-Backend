package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BoardListSortEnum;
import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.mapper.BoardMapper;
import cn.wegfan.forum.mapper.UserMapper;
import cn.wegfan.forum.model.entity.Board;
import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "board")
@Transactional(rollbackFor = Exception.class)
public class BoardServiceImpl extends ServiceImpl<BoardMapper, Board> implements BoardService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(key = "'notDeletedIdList'")
    public List<Long> listNotDeletedBoardIds() {
        return boardMapper.selectNotDeletedBoardIdList();
    }

    @Override
    public Board getNotDeletedBoardByNameAndCategoryId(String name, Long categoryId) {
        return boardMapper.selectNotDeletedByNameAndCategoryId(name, categoryId);
    }

    @Override
    public Board getNotDeletedBoardByBoardId(Long boardId) {
        return boardMapper.selectNotDeletedByBoardId(boardId);
    }

    @Override
    @CacheEvict(key = "'notDeletedIdList'")
    public int addBoard(Board board) {
        board.setId(null);
        Date now = new Date();
        board.setCreateTime(now);
        board.setUpdateTime(now);
        int result = boardMapper.insert(board);
        return result;
    }

    @Override
    public int updateBoard(Board board) {
        Date now = new Date();
        board.setUpdateTime(now);
        int result = boardMapper.updateById(board);
        return result;
    }

    @Override
    @CacheEvict(key = "'notDeletedIdList'")
    public int deleteBoardByBoardId(Long boardId) {
        return boardMapper.deleteByBoardId(boardId);
    }

    @Override
    public List<Board> listNotDeletedAdminBoardsByUserId(Long userId) {
        return boardMapper.selectNotDeletedAdminBoardListByUserId(userId);
    }

    @Override
    public List<Board> batchListNotDeletedBoardsByBoardIds(List<Long> idList) {
        if (idList.isEmpty()) {
            return Collections.emptyList();
        }
        return boardMapper.selectList(Wrappers.<Board>lambdaQuery()
                .in(Board::getId, idList));
    }

    @Override
    public boolean checkBoardAdminByUserIdAndBoardId(Long userId, Long boardId) {
        return boardMapper.checkBoardAdminByUserIdAndBoardId(userId, boardId);
    }

    @Override
    public Page<Board> listNotDeletedBoardsByPage(Page<?> page, BoardListSortEnum sortEnum) {
        return boardMapper.selectNotDeletedBoardListByPage(page, sortEnum.getOrderBySql());
    }

    @Override
    public Page<Board> listNotDeletedAllAdminBoardsByPageAndUserId(Page<?> page, Long userId,
                                                                   BoardListSortEnum sortEnum) {
        User user = userMapper.selectNotDeletedByUserId(userId);
        // 如果管理员或超级版主则直接获取所有分区
        if (user.getAdmin() || user.getSuperBoardAdmin()) {
            return boardMapper.selectNotDeletedBoardListByPage(page, sortEnum.getOrderBySql());
        } else {
            return boardMapper.selectNotDeletedAdminBoardListIncludingCategoryAdminByUserId(page, userId,
                    sortEnum.getOrderBySql());
        }
    }

    @Override
    public List<Board> listNotDeletedAllAdminBoardsByUserId(Long userId, BoardListSortEnum sortEnum) {
        return listNotDeletedAllAdminBoardsByPageAndUserId(Constant.UNPAGED_PAGE, userId,
                sortEnum).getRecords();
    }

    @Override
    public List<Board> listHomepageBoardsByUserId(Long userId) {
        return boardMapper.selectHomepageBoardListByUserId(userId);
    }

}
