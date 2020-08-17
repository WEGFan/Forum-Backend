package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.UserLoginRequestVo;
import cn.wegfan.forum.model.vo.request.UserRegisterRequestVo;
import cn.wegfan.forum.model.vo.response.UserLoginResponseVo;
import cn.wegfan.forum.model.vo.response.UserRoleResponseVo;
import cn.wegfan.forum.util.BusinessException;
import cn.wegfan.forum.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardAdminService boardAdminService;

    @Autowired
    private CategoryAdminService categoryAdminService;

    public UserLoginResponseVo login(UserLoginRequestVo requestVo) {
        // TODO: 验证码
        User user = userService.getNotDeletedUserByUsername(requestVo.getUsername());
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.WRONG_USERNAME_OR_PASSWORD);
        }
        // TODO: 判断帐号是否被禁止登录

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getId().toString(), requestVo.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            throw new BusinessException(BusinessErrorEnum.WRONG_USERNAME_OR_PASSWORD);
        }

        // TODO: ip
        userService.updateUserLoginTimeAndIpByUserId(user.getId(), new Date(), "0.0.0.0");

        UserLoginResponseVo responseVo = mapperFacade.map(user, UserLoginResponseVo.class);

        // 设置权限对象
        UserRoleResponseVo roleVo = new UserRoleResponseVo();
        roleVo.setAdmin(user.getAdmin());
        roleVo.setSuperBoardAdmin(user.getSuperBoardAdmin());
        roleVo.setBoardAdmin(boardAdminService.countByUserId(user.getId()) > 0);
        roleVo.setCategoryAdmin(categoryAdminService.countByUserId(user.getId()) > 0);
        responseVo.setPermission(roleVo);

        return responseVo;
    }

    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        // 如果用户本身未登录
        if (subject == null) {
            throw new BusinessException(BusinessErrorEnum.UserNotLogin);
        }
        subject.logout();
    }

    public void register(UserRegisterRequestVo requestVo) {
        // TODO: 验证码
        User sameUsernameUser = userService.getNotDeletedUserByUsername(requestVo.getUsername());
        if (sameUsernameUser != null) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_USERNAME);
        }

        User user = mapperFacade.map(requestVo, User.class);
        user.setPassword(PasswordUtil.encryptPasswordBcrypt(user.getPassword()));

        userService.addUserByRegister(user);
    }

}
