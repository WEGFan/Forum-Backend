package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.BoardAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardAdminMapper extends BaseMapper<BoardAdmin> {

    List<BoardAdmin> selectListByBoardId(Long boardId);

    List<BoardAdmin> selectListByUserId(Long userId);

    long countByUserId(Long userId);

}
