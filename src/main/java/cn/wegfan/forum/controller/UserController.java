package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.request.*;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.UserServiceFacade;
import cn.wegfan.forum.util.BusinessException;
import cn.wegfan.forum.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private UserServiceFacade userServiceFacade;

    /**
     * 登录
     */
    @PostMapping("login")
    public ResultVo login(@Valid @RequestBody UserLoginRequestVo requestVo) {
        return ResultVo.success(userServiceFacade.login(requestVo));
    }

    /**
     * 退出登录
     */
    @PostMapping("logout")
    public ResultVo logout() {
        userServiceFacade.logout();
        return ResultVo.success(null);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public ResultVo register(@Valid @RequestBody UserRegisterRequestVo requestVo) {
        userServiceFacade.register(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 发送邮件验证码
     */
    @PostMapping("send-email-verify-code")
    public ResultVo sendEmailVerifyCode() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 找回密码
     */
    @PostMapping("reset-password")
    public ResultVo resetPassword() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 查看指定用户的个人中心
     */
    @GetMapping("user-info")
    public ResultVo getUserInfo(@RequestParam Integer userId) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 修改个人资料
     */
    @PostMapping("update-info")
    public ResultVo updatePersonalUserInfo(@Valid @RequestBody UpdatePersonalUserInfoRequestVo requestVo) {
        userServiceFacade.updatePersonalUserInfo(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 修改头像
     */
    @PostMapping("update-avatar")
    public ResultVo updateAvatar(@RequestParam("file") MultipartFile multipartFile) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 修改密码
     */
    @PostMapping("update-password")
    public ResultVo updatePassword(@Valid @RequestBody UpdatePersonalPasswordRequestVo requestVo) {
        userServiceFacade.updatePersonalPassword(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 激活邮箱
     */
    @PostMapping("verify-email")
    public ResultVo verifyEmail() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 修改邮箱
     */
    @PostMapping("update-email")
    public ResultVo updateEmail() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 获取用户在指定板块的权限
     */
    @GetMapping("board-permission")
    public ResultVo getBoardPermission(@RequestParam Integer boardId) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】查看用户列表
     */
    @GetMapping("user-list")
    public ResultVo getUserList(@RequestParam(required = false) String username,
                                @RequestParam(required = false) String userType,
                                @RequestParam Integer page,
                                @RequestParam Integer count) {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】查看用户名列表
     */
    @GetMapping("username-list")
    public ResultVo getUsernameList(@RequestParam(required = false) String user) {
        return ResultVo.success(userServiceFacade.getUsernameList(user));
    }

    /**
     * 【管理】添加用户
     */
    @PostMapping("add-user")
    public ResultVo addUser() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】删除用户
     */
    @PostMapping("delete-user")
    public ResultVo deleteUser(@Valid @RequestBody IdRequestVo requestVo) {
        userServiceFacade.deleteUser(requestVo.getId());
        return ResultVo.success(null);
    }

    /**
     * 【管理】设置版主
     */
    @PostMapping("update-board-admin")
    public ResultVo updateBoardAdmin(@Valid @RequestBody UpdateBoardAdminRequestVo requestVo) {
        userServiceFacade.updateBoardAdmin(requestVo.getUserId(), requestVo.getBoardIdList());
        return ResultVo.success(null);
    }

    /**
     * 【管理】设置分区版主
     */
    @PostMapping("update-category-admin")
    public ResultVo updateCategoryAdmin(@Valid @RequestBody UpdateCategoryAdminRequestVo requestVo) {
        userServiceFacade.updateCategoryAdmin(requestVo.getUserId(), requestVo.getCategoryIdList());
        return ResultVo.success(null);
    }

    /**
     * 【管理】添加超级版主
     */
    @PostMapping("add-super-board-admin")
    public ResultVo addSuperBoardAdmin() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】删除超级版主
     */
    @PostMapping("delete-super-board-admin")
    public ResultVo deleteSuperBoardAdmin() {
        throw new BusinessException(BusinessErrorEnum.NotImplemented);
    }

    /**
     * 【管理】修改用户的论坛权限
     */
    @PostMapping("update-forum-permission")
    public ResultVo updateForumPermission(@Valid @RequestBody UpdateForumPermissionRequestVo requestVo) {
        userServiceFacade.updateForumPermission(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】修改用户的板块权限
     */
    @PostMapping("update-board-permission")
    public ResultVo updateBoardPermission(@Valid @RequestBody UpdateBoardPermissionRequestVo requestVo) {
        userServiceFacade.updateBoardPermission(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】修改用户资料
     */
    @PostMapping("update-user-info")
    public ResultVo updateUserInfo(@RequestBody UpdateUserInfoRequestVo requestVo) {
        // 如果密码是空字符串的话说明不修改密码，设成null
        if (StringUtils.isEmpty(requestVo.getPassword())) {
            requestVo.setPassword(null);
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        userServiceFacade.updateUserInfo(requestVo);
        return ResultVo.success(null);
    }

}
