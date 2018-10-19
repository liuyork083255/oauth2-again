package liu.york.oauth2.authorization.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 对于用户信息来说，不存在四种登录模式，这种概念是针对客户端信息的
     */
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        String username = "user";
        String password = passwordEncoder.encode("123456");

        return new User(
                // 数据库查询到的用户名
                username,
                // 数据库查询到的密码
                password,
                // 数据库查询到的的权限列表，需要告诉 security 当前拥有哪些权限
                // 设置权限的时候需要注意，AuthorityUtils 工具类对应的是 http 配置中的 hasAuthority 类型
                // 也就是说如果 http 配置使用hasRole 控制，那么这里需要添加 ROLE_ 前缀
                AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
    }
}