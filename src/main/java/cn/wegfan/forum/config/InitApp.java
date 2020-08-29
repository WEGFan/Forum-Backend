package cn.wegfan.forum.config;

import cn.hutool.core.io.FileUtil;
import cn.wegfan.forum.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class InitApp implements ApplicationRunner {

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
        log.info("app start running");
    }

}
