package cn.wegfan.forum.service;

import cn.hutool.core.util.StrUtil;
import cn.wegfan.forum.mapper.PermissionMapper;
import cn.wegfan.forum.model.entity.Permission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Cacheable(key = "'forum:' + #userId", unless = "#result == null")
    public Permission getForumPermissionByUserId(Long userId) {
        return permissionMapper.selectForumPermissionByUserId(userId);
    }

    @Override
    @Cacheable(key = "'board:' + #boardId", unless = "#result == null")
    public Permission getBoardPermissionByBoardId(Long boardId) {
        return permissionMapper.selectBoardPermissionByBoardId(boardId);
    }

    @Override
    @Cacheable(key = "'userBoard:' + #userId + '_' + #boardId", unless = "#result == null")
    public Permission getUserBoardPermissionByUserIdAndBoardId(Long userId, Long boardId) {
        return permissionMapper.selectUserBoardPermissionByUserIdAndBoardId(userId, boardId);
    }

    @Override
    @CacheEvict(key = "'forum:' + #permission.userId")
    public boolean addOrUpdateForumPermission(Permission permission) {
        return saveOrUpdate(permission, Wrappers.<Permission>lambdaUpdate()
                .eq(Permission::getUserId, permission.getUserId())
                .isNull(Permission::getBoardId));
    }

    @Override
    @CacheEvict(key = "'board:' + #permission.boardId")
    public boolean addOrUpdateBoardPermission(Permission permission) {
        return saveOrUpdate(permission, Wrappers.<Permission>lambdaUpdate()
                .eq(Permission::getBoardId, permission.getBoardId())
                .isNull(Permission::getUserId));
    }

    @Override
    @CacheEvict(key = "'userBoard:' + #permission.userId + '_' + #permission.boardId")
    public boolean addOrUpdateUserBoardPermission(Permission permission) {
        return saveOrUpdate(permission, Wrappers.<Permission>lambdaUpdate()
                .eq(Permission::getUserId, permission.getUserId())
                .eq(Permission::getBoardId, permission.getBoardId()));
    }

    @Override
    public List<Permission> listBoardPermissionsByUserId(Long userId) {
        List<Permission> permissionList = permissionMapper.selectBoardPermissionListByUserId(userId);
        permissionList.forEach(item -> {
            String key = StrUtil.format("forum:permission::userBoard:{}_{}", userId, item.getBoardId());
            redisTemplate.opsForValue().set(key, item);
        });
        return permissionList;
    }

}
