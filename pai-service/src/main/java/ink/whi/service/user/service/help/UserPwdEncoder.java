package ink.whi.service.user.service.help;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 密码加密工具类
 */
@Component
public class UserPwdEncoder {

    // 密码加盐，更推荐的做法是每个用户都使用独立的盐，提高安全性
    @Value("${security.salt}")
    private String salt;

    // 密码加盐的位置
    @Value("${security.salt-index}")
    private Integer saltIndex;

    /**
     * 密码加密，后续接入SpringSecurity之后，可以使用 PasswordEncoder 进行替换
     * @param plainPwd
     * @param encPwd
     * @return 判断是否匹配
     */
    public boolean match(String plainPwd, String encPwd) {
        if (plainPwd.length() > saltIndex) {
            plainPwd = plainPwd.substring(0, saltIndex) + salt + plainPwd.substring(saltIndex);
        } else {
            plainPwd = plainPwd + salt;
        }

        return Objects.equals(DigestUtils.md5DigestAsHex(plainPwd.getBytes(StandardCharsets.UTF_8)), encPwd);
    }

}
