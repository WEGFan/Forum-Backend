package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.BoardAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BoardAdminService extends IService<BoardAdmin> {

    List<BoardAdmin> listByBoardId(Long boardId);

    List<BoardAdmin> listByUserId(Long userId);

    long countByUserId(Long userId);

}
