package liu.york.oauth2.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 一个资源服务（可以和授权服务在同一个应用中，当然也可以分离开成为两个不同的应用程序）提供一些受token令牌保护的资源，
 * Spring OAuth提供者是通过Spring Security authentication filter 即验证过滤器来实现的保护
 *
 * 可以配置的属性：
 *      tokenServices：ResourceServerTokenServices 类的实例，用来实现令牌服务。
 *      resourceId：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。
 *      其他的拓展属性例如 tokenExtractor 令牌提取器用来提取请求中的令牌。
 *      请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是受保护资源服务的全部路径。
 *      受保护资源的访问规则，默认的规则是简单的身份验证（plain authenticated）。
 *      其他的自定义权限保护规则通过 HttpSecurity 来进行配置。
 *
 * @EnableResourceServer 注解自动增加了一个类型为 OAuth2AuthenticationProcessingFilter 的过滤器链
 *
 * ResourceServerTokenServices 是组成授权服务的另一半，如果授权服务和资源服务在同一个应用程序上的话，
 * 可以使用 DefaultTokenServices ，这样的话，就不用考虑关于实现所有必要的接口的一致性问题，这通常是很困难的。
 * 如果资源服务器是分离开的，那么就必须要确保能够有匹配授权服务提供的 ResourceServerTokenServices，它知道如何对令牌进行解码。
 *
 *
 *
 *
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
//                .antMatchers("/res1/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
                .antMatchers("/res2/**").authenticated();//配置order访问控制，必须认证过后才可以访问
    }
}