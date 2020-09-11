package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BoardListSortEnum;
import cn.wegfan.forum.model.entity.Board;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BoardService extends IService<Board> {

    List<Long> listNotDeletedBoardIds();

    Board getNotDeletedBoardByNameAndCategoryId(String name, Long categoryId);

    Board getNotDeletedBoardByBoardId(Long boardId);

    int addBoard(Board board);

    int updateBoard(Board board);

    int deleteBoardByBoardId(Long boardId);

    int deleteBoardByCategoryId(Long categoryId);

    List<Board> listNotDeletedAdminBoardsByUserId(Long userId);

    List<Board> batchListNotDeletedBoardsByBoardIds(List<Long> idList);

    boolean checkBoardAdminByUserIdAndBoardId(Long userId, Long boardId);

    Page<Board> listNotDeletedBoardsByPage(Page<?> page, BoardListSortEnum sortEnum);

    Page<Board> listNotDeletedAllAdminBoardsByPageAndUserId(Page<?> page, Long userId, BoardListSortEnum sortEnum);

    List<Board> listNotDeletedAllAdminBoardsByUserId(Long userId, BoardListSortEnum sortEnum);

    List<Long> listNotDeletedAllAdminBoardIdsByUserId(Long userId);

    List<Board> listHomepageBoardsByUserId(Long userId);

}
