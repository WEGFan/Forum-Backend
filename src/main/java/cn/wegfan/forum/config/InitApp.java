package cn.wegfan.forum.config;

import cn.hutool.core.io.FileUtil;
import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.service.UserService;
import cn.wegfan.forum.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class InitApp implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        File[] folderList = new File[] {
                Constant.AVATAR_PATH.toFile(),
                Constant.ATTACHMENT_PATH.toFile(),
                Constant.IMAGE_PATH.toFile()
        };
        for (File file : folderList) {
            FileUtil.mkdir(file);
        }

        // 如果数据库里没有用户，则创建管理员账户
        if (userService.countNotDeletedUsers() == 0) {
            String username = "admin";
            String password = "admin123";

            User user = new User();
            user.setUsername(username);
            user.setNickname("管理员");
            user.setEmail("");
            user.setAdmin(true);
            user.setPassword(PasswordUtil.encryptPasswordBcrypt(password));

            userService.addUserByAdmin(user);
            log.info("已创建管理员帐号，用户名：{}，密码：{}", username, password);
        }

        log.info("app start running");
    }

}
