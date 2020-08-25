package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.BoardAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BoardAdminMapper extends BaseMapper<BoardAdmin> {

    List<BoardAdmin> selectListByBoardId(Long boardId);

    List<BoardAdmin> selectListByUserId(Long userId);

    Set<Long> selectUserIdSetByBoardId(Long boardId);

    Set<Long> selectBoardIdSetByUserId(Long userId);

    long countByUserId(Long userId);

    int deleteByBoardId(Long boardId);

}
