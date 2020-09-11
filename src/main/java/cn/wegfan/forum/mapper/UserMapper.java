package cn.wegfan.forum.mapper;

import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    long countNotDeletedUser();

    User selectByUserId(Long userId);

    User selectNotDeletedByUserId(Long userId);

    User selectNotDeletedByUsername(String username);

    User selectNotDeletedByEmail(String email);

    int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp);

    int updateUserPersonalInfoByUserId(Long userId, String nickname, SexEnum sex, String signature);

    int updateUserPasswordByUserId(Long userId, String newPassword);

    int deleteByUserId(Long userId);

    List<User> selectListByName(String user);

    // TODO: 合并
    Page<User> selectNotDeletedUserListByPageAndUsername(Page<?> page, String username, Long userId);

    Page<User> selectNotDeletedNormalUserListByPageAndUsername(Page<?> page, String username, Long userId);

    Page<User> selectNotDeletedBoardAdminListByPageAndUsername(Page<?> page, String username, Long userId);

    Page<User> selectNotDeletedCategoryAdminListByPageAndUsername(Page<?> page, String username, Long userId);

    Page<User> selectNotDeletedSuperBoardAdminListByPageAndUsername(Page<?> page, String username, Long userId);

    Page<User> selectNotDeletedAdminListByPageAndUsername(Page<?> page, String username, Long userId);

    Page<User> selectNotDeletedBanVisitListByPageAndUsername(Page<?> page, String username, Long userId);

    Page<User> selectNotDeletedBanCreateTopicAndReplyListByPageAndUsername(Page<?> page, String username, Long userId);

    List<User> selectNotDeletedCategoryAdminListByCategoryId(Long categoryId);

    List<User> selectNotDeletedBoardAdminListByBoardId(Long boardId);

    int updateUserAvatarByUserId(Long userId, String avatarPath);

    int increaseUserTopicCountByUserId(Long userId);

    int increaseUserReplyCountByUserId(Long userId);

    int updateUserEmailVerifiedByUserId(Long userId);

    int updateUserEmailByUserId(Long userId, String email);

}
