package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Board;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper extends BaseMapper<Board> {

    List<Long> selectNotDeletedBoardIdList();

    Board selectNotDeletedByNameAndCategoryId(String name, Long categoryId);

    Board selectNotDeletedByBoardId(Long boardId);

    int deleteByBoardId(Long boardId);

    List<Board> selectNotDeletedAdminBoardListWithBoardAdminByUserId(Long userId);

    boolean checkBoardAdminByUserIdAndBoardId(Long userId, Long boardId);

    Page<Board> selectNotDeletedBoardListByPage(Page<?> page, String orderBy);

    Page<Board> selectNotDeletedAdminBoardListWithBoardCategoryAdminByUserId(Page<?> page, Long userId, String orderBy);

}
