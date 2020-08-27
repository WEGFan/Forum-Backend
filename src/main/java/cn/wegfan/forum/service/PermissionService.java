package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PermissionService extends IService<Permission> {

    Permission getForumPermissionByUserId(Long userId);

    Permission getBoardPermissionByBoardId(Long boardId);

    Permission getUserBoardPermissionByUserIdAndBoardId(Long userId, Long boardId);

    boolean addOrUpdateForumPermission(Permission permission);

    boolean addOrUpdateBoardPermission(Permission permission);

    boolean addOrUpdateUserBoardPermission(Permission permission);

    List<Permission> listBoardPermissionsByUserId(Long userId);

}
