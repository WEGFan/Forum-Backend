package cn.wegfan.forum.controller;

import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.constant.UserTypeEnum;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.*;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.service.CaptchaService;
import cn.wegfan.forum.service.UserService;
import cn.wegfan.forum.service.UserServiceFacade;
import cn.wegfan.forum.util.PaginationUtil;
import cn.wegfan.forum.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private UserServiceFacade userServiceFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 登录
     */
    @PostMapping("login")
    public ResultVo login(@RequestBody UserLoginRequestVo requestVo) {
        // 校验验证码
        if (!captchaService.checkCaptcha(requestVo.getVerifyCodeRandom(), requestVo.getVerifyCode())) {
            requestVo.setVerifyCode("");
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
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
    public ResultVo register(@RequestBody UserRegisterRequestVo requestVo) {
        // 校验验证码
        if (!captchaService.checkCaptcha(requestVo.getVerifyCodeRandom(), requestVo.getVerifyCode())) {
            requestVo.setVerifyCode("");
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        userServiceFacade.register(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 发送邮件验证码
     */
    @PostMapping("send-email-verify-code")
    public ResultVo sendEmailVerifyCode(@RequestBody SendEmailVerifyCodeRequestVo requestVo) {
        // 校验验证码
        if (!captchaService.checkCaptcha(requestVo.getVerifyCodeRandom(), requestVo.getVerifyCode())) {
            requestVo.setVerifyCode("");
        }
        // 校验邮箱是否输入
        User user = userService.getCurrentLoginUser();
        if (user != null) {
            requestVo.setEmail(user.getEmail());
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        userServiceFacade.sendEmailVerifyCode(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 找回密码
     */
    @PostMapping("reset-password")
    public ResultVo resetPassword(@RequestBody UserResetPasswordRequestVo requestVo) {
        // 校验邮箱验证码
        if (!captchaService.checkEmailVerifyCode(requestVo.getEmail(), requestVo.getEmailVerifyCode())) {
            requestVo.setEmailVerifyCode("");
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        userServiceFacade.resetPassword(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 查看指定用户的个人中心
     */
    @GetMapping("user-info")
    public ResultVo getUserInfo(@RequestParam Long userId) {
        return ResultVo.success(userServiceFacade.getUserCenterInfo(userId));
    }

    /**
     * 修改个人资料
     */
    @PostMapping("update-info")
    @RequiresUser
    public ResultVo updatePersonalUserInfo(@Valid @RequestBody UpdatePersonalUserInfoRequestVo requestVo) {
        userServiceFacade.updatePersonalUserInfo(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 修改头像
     */
    @PostMapping("update-avatar")
    @RequiresUser
    public ResultVo updateAvatar(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResultVo.success(userServiceFacade.updateUserAvatar(multipartFile));
    }

    /**
     * 修改密码
     */
    @PostMapping("update-password")
    @RequiresUser
    public ResultVo updatePassword(@RequestBody UpdatePersonalPasswordRequestVo requestVo) {
        // 校验邮箱验证码
        User user = userService.getCurrentLoginUser();
        if (!captchaService.checkEmailVerifyCode(user.getEmail(), requestVo.getEmailVerifyCode())) {
            requestVo.setEmailVerifyCode("");
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        userServiceFacade.updatePersonalPassword(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 激活邮箱
     */
    @PostMapping("verify-email")
    @RequiresUser
    public ResultVo verifyEmail(@RequestBody UserVerifyEmailRequestVo requestVo) {
        // 校验邮箱验证码
        User user = userService.getCurrentLoginUser();
        if (!captchaService.checkEmailVerifyCode(user.getEmail(), requestVo.getEmailVerifyCode())) {
            requestVo.setEmailVerifyCode("");
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        userServiceFacade.verifyEmail(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 修改邮箱
     */
    @PostMapping("update-email")
    @RequiresUser
    public ResultVo updateEmail(@RequestBody UserUpdateEmailRequestVo requestVo) {
        // 校验邮箱验证码
        if (!captchaService.checkEmailVerifyCode(requestVo.getEmail(), requestVo.getEmailVerifyCode())) {
            requestVo.setEmailVerifyCode("");
        }
        ValidateUtil.tryValidateAndThrow(requestVo);
        userServiceFacade.updateEmail(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 获取用户在指定板块的权限
     */
    @GetMapping("board-permission")
    public ResultVo getBoardPermission(@RequestParam Long boardId) {
        return ResultVo.success(userServiceFacade.getUserBoardPermission(boardId));
    }

    /**
     * 【管理】查看用户列表
     */
    @GetMapping("user-list")
    @RequiresUser
    public ResultVo getUserList(@RequestParam(required = false) Long userId,
                                @RequestParam(required = false) String username,
                                @RequestParam(required = false) String userType,
                                @RequestParam Long page,
                                @RequestParam Long count) {
        count = PaginationUtil.clampPageSize(count);
        UserTypeEnum userTypeEnum = UserTypeEnum.fromValue(userType, UserTypeEnum.ALL);
        return ResultVo.success(userServiceFacade.getUserList(userId, username, userTypeEnum, page, count));
    }

    /**
     * 【管理】查看用户名列表
     */
    @GetMapping("username-list")
    @RequiresUser
    public ResultVo getUsernameList(@RequestParam(required = false) String user) {
        return ResultVo.success(userServiceFacade.getUsernameList(user));
    }

    /**
     * 【管理】添加用户
     */
    @PostMapping("add-user")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo addUser(@Valid @RequestBody AddUserRequestVo requestVo) {
        userServiceFacade.addUserByAdmin(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】删除用户
     */
    @PostMapping("delete-user")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo deleteUser(@Valid @RequestBody IdRequestVo requestVo) {
        userServiceFacade.deleteUser(requestVo.getId());
        return ResultVo.success(null);
    }

    /**
     * 【管理】设置版主
     */
    @PostMapping("update-board-admin")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo updateBoardAdmin(@Valid @RequestBody UpdateBoardAdminRequestVo requestVo) {
        userServiceFacade.updateBoardAdmin(requestVo.getUserId(), requestVo.getBoardIdList());
        return ResultVo.success(null);
    }

    /**
     * 【管理】设置分区版主
     */
    @PostMapping("update-category-admin")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo updateCategoryAdmin(@Valid @RequestBody UpdateCategoryAdminRequestVo requestVo) {
        userServiceFacade.updateCategoryAdmin(requestVo.getUserId(), requestVo.getCategoryIdList());
        return ResultVo.success(null);
    }

    /**
     * 【管理】修改用户的论坛权限
     */
    @PostMapping("update-forum-permission")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo updateForumPermission(@Valid @RequestBody UpdateForumPermissionRequestVo requestVo) {
        userServiceFacade.updateForumPermission(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】修改用户的板块权限
     */
    @PostMapping("update-board-permission")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
    public ResultVo updateUserBoardPermission(@Valid @RequestBody UpdateBoardPermissionRequestVo requestVo) {
        userServiceFacade.updateUserBoardPermission(requestVo);
        return ResultVo.success(null);
    }

    /**
     * 【管理】修改用户资料
     */
    @PostMapping("update-user-info")
    @RequiresPermissions(Constant.SHIRO_PERMISSION_ADMIN)
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
