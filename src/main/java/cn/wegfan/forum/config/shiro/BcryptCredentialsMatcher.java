package cn.wegfan.forum.config.shiro;

import cn.wegfan.forum.util.PasswordUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class BcryptCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        // 要验证的明文密码
        String plainPassword = new String(userToken.getPassword());
        // 数据库中的加密后的密文
        String correctPassword = info.getCredentials().toString();
        return PasswordUtil.checkPasswordBcrypt(plainPassword, correctPassword);
    }

}
