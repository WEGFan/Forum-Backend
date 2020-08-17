package cn.wegfan.forum.util;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 * 测试 {@link PasswordUtil} 工具类
 */
@Slf4j
class PasswordUtilTest {

    @Test
    void testHashPassword() {
        String plainPassword = "wegfan";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        Assertions.assertEquals("88df68e4f2bd6dd021b40b063ee80e1ac4c894cea3f8a5b9e453278f4c6dff09",
                hashedPassword);
    }

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