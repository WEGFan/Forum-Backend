package cn.wegfan.forum.service;

import cn.wegfan.forum.mapper.PermissionMapper;
import cn.wegfan.forum.model.entity.Permission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "permission")
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public Permission getForumPermissionByUserId(Long userId) {
        return permissionMapper.selectForumPermissionByUserId(userId);
    }

    @Override
    public Permission getBoardPermissionByBoardId(Long boardId) {
        return permissionMapper.selectBoardPermissionByBoardId(boardId);
    }

    @Override
    public Permission getUserBoardPermissionByUserIdAndBoardId(Long userId, Long boardId) {
        return permissionMapper.selectUserBoardPermissionByUserIdAndBoardId(userId, boardId);
    }

    @Override
    public boolean addOrUpdateForumPermission(Permission permission) {
        return saveOrUpdate(permission, Wrappers.<Permission>lambdaUpdate()
                .eq(Permission::getUserId, permission.getUserId())
                .isNull(Permission::getBoardId));
    }

    @Override
    public boolean addOrUpdateBoardPermission(Permission permission) {
        return saveOrUpdate(permission, Wrappers.<Permission>lambdaUpdate()
                .eq(Permission::getBoardId, permission.getBoardId())
                .isNull(Permission::getUserId));
    }

    @Override
    public boolean addOrUpdateUserBoardPermission(Permission permission) {
        return saveOrUpdate(permission, Wrappers.<Permission>lambdaUpdate()
                .eq(Permission::getUserId, permission.getUserId())
                .eq(Permission::getBoardId, permission.getBoardId()));
    }

    @Override
    public List<Permission> listBoardPermissionsByUserId(Long userId) {
        return permissionMapper.selectBoardPermissionListByUserId(userId);
    }

}
