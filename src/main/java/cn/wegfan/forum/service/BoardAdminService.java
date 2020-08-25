package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.BoardAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface BoardAdminService extends IService<BoardAdmin> {

    List<BoardAdmin> listByBoardId(Long boardId);

    List<BoardAdmin> listByUserId(Long userId);

    long countByUserId(Long userId);

    boolean batchAddBoardAdminByUserId(Long userId, Set<Long> boardIdList);

    boolean batchDeleteBoardAdminByUserId(Long userId, Set<Long> boardIdList);

    int deleteBoardAdminByBoardId(Long boardId);

    Set<Long> listUserIdByBoardId(Long boardId);

    Set<Long> listBoardIdByUserId(Long userId);

}
