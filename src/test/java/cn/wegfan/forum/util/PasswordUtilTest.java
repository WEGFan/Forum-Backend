package cn.wegfan.forum.util;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

/**
 * 测试 {@link PasswordUtil} 工具类
 */
@Slf4j
class PasswordUtilTest {

    @RepeatedTest(5)
    void testCheckPassword() {
        // 随机生成明文密码
        String plainPassword = RandomUtil.randomString(64);
        log.info("plainPassword = {}", plainPassword);

        for (int i = 0; i < 10; i++) {
            String encryptedPassword = PasswordUtil.encryptPasswordBcrypt(plainPassword);
            boolean correctResult = PasswordUtil.checkPasswordBcrypt(plainPassword, encryptedPassword);
            String wrongPassword = RandomUtil.randomString(32);
            boolean wrongResult = PasswordUtil.checkPasswordBcrypt(wrongPassword, encryptedPassword);
            Assertions.assertTrue(correctResult);
            Assertions.assertFalse(wrongResult);
            log.info("#{}: encryptedPassword = {}", i, encryptedPassword);
        }
    }

}