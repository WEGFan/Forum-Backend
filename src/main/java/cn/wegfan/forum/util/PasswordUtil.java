package cn.wegfan.forum.util;

import cn.hutool.crypto.digest.DigestUtil;
import org.mindrot.jbcrypt.BCrypt;

/**
 * 密码工具类
 */
public class PasswordUtil {

    /**
     * 使用SHA-256对密码哈希
     *
     * @param plainPassword 明文密码
     *
     * @return 哈希后的密码，64位
     */
    private static String hashPassword(String plainPassword) {
        // base91.encode(b''.join(
        //     h(x.encode()).digest()
        //     for (h, x) in zip([hashlib.sha256, hashlib.sha384, hashlib.sha512], [ ... ])
        // ))
        String hashed = DigestUtil.sha256Hex(
                "4cJ+WqE&1)8g>%IB\"u6_N.1+nrv~u!X3[Y_UT2a8(OOKLcfsMQ&y3_?vVh9)enImBpG1Lm`@I=zo~\"I8PsWfykr]0" +
                        plainPassword +
                        "nsjeM2ap>h*5WLhQ;k+%/BhV^qstUu9Wl890IX2O9+CkR=~t[+2]800%iZx.r!9T:S39)._4.Y@>0]6?@l}wdnyRB");
        return hashed;
    }

    /**
     * 使用BCrypt对密码加密
     *
     * @param plainPassword 明文密码
     *
     * @return 加密后的密码
     */
    public static String encryptPasswordBcrypt(String plainPassword) {
        // 由于bcrypt最多只支持72位，所以先对密码进行sha256哈希
        String salt = BCrypt.gensalt(10);
        String hashed = hashPassword(plainPassword);
        String encryptedPassword = BCrypt.hashpw(hashed, salt);
        return encryptedPassword;
    }

    /**
     * 使用BCrypt检查密码是否正确
     *
     * @param plainPassword   明文密码
     * @param correctPassword 正确密码
     *
     * @return 密码是否正确
     */
    public static boolean checkPasswordBcrypt(String plainPassword, String correctPassword) {
        String hashed = hashPassword(plainPassword);
        boolean result = BCrypt.checkpw(hashed, correctPassword);
        return result;
    }

}
