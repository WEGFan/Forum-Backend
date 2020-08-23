package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.Board;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BoardService extends IService<Board> {

    Board getNotDeletedBoardByNameAndCategoryId(String name, Long categoryId);

    Board getNotDeletedBoardByBoardId(Long boardId);

    int addBoard(Board board);

    int updateBoard(Board board);

    int deleteBoardByBoardId(Long boardId);

}
