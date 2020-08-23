package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.BoardMapper;
import cn.wegfan.forum.model.entity.Board;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@CacheConfig(cacheNames = "board")
@Transactional(rollbackFor = Exception.class)
public class BoardServiceImpl extends ServiceImpl<BoardMapper, Board> implements BoardService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public Board getNotDeletedBoardByNameAndCategoryId(String name, Long categoryId) {
        return boardMapper.selectNotDeletedByNameAndCategoryId(name, categoryId);
    }

    @Override
    public Board getNotDeletedBoardByBoardId(Long boardId) {
        return boardMapper.selectNotDeletedByBoardId(boardId);
    }

    @Override
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
    public int deleteBoardByBoardId(Long boardId) {
        return boardMapper.deleteByBoardId(boardId);
    }

}
