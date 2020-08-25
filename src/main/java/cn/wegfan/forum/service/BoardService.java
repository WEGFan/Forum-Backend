package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.Board;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BoardService extends IService<Board> {

    List<Long> listNotDeletedBoardIds();

    Board getNotDeletedBoardByNameAndCategoryId(String name, Long categoryId);

    Board getNotDeletedBoardByBoardId(Long boardId);

    int addBoard(Board board);

    int updateBoard(Board board);

    int deleteBoardByBoardId(Long boardId);

}
