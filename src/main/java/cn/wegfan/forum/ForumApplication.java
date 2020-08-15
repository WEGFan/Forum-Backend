package cn.wegfan.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
public class ForumApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ForumApplication.class);
        ApplicationContext context = application.run(args);
    }

}
