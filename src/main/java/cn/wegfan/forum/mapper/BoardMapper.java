package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Board;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper extends BaseMapper<Board> {

    List<Long> selectNotDeletedBoardIdList();

    Board selectNotDeletedByNameAndCategoryId(String name, Long categoryId);

    Board selectNotDeletedByBoardId(Long boardId);

    int deleteByBoardId(Long boardId);

    List<Board> selectNotDeletedAdminBoardListByUserId(Long userId);

}
