package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.Board;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMapper extends BaseMapper<Board> {

    Board selectNotDeletedByNameAndCategoryId(String name, Long categoryId);

    Board selectNotDeletedByBoardId(Long boardId);

    int deleteByBoardId(Long boardId);

}
