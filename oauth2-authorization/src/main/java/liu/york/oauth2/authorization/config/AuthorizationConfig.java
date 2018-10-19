package liu.york.oauth2.authorization.config;

import liu.york.oauth2.authorization.info.ClientDetailService;
import liu.york.oauth2.authorization.info.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

   @Autowired
   private ClientDetailService clientDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 配置客户端详情信息
     *    能够使用内存或者JDBC来实现客户端详情服务
     *    客户端详情（Client Details）能够在应用程序运行的时候进行更新，可以通过访问底层的存储服务
     *    （例如将客户端详情存储在一个关系数据库的表中，就可以使用 JdbcClientDetailsService）
     *    或者通过也可以实现 ClientDetailsService 接口来进行管理
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /*
         *  clientId：（必须的）用来标识客户的Id。
         *  secret：（需要值得信任的客户端）客户端安全码，如果有的话。
         *  scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
         *  authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
         *  authorities：此客户端可以使用的权限（基于Spring Security authorities）
         */
//        clients.inMemory()
//                .withClient("client")
//                .authorizedGrantTypes("password", "refresh_token","authorization_code")
//                .secret("123456")
//                .scopes()
//                .authorities();

        clients.withClientDetails(clientDetailService);

    }


    /**
     * 授权是使用 AuthorizationEndpoint 这个端点来进行控制的，
     * 能够使用 AuthorizationServerEndpointsConfigurer 这个对象的实例来进行配置，如
     * 果不进行设置的话，默认是除了资源所有者密码（password）授权类型以外，支持其余所有标准授权类型的（RFC6749）
     *
     * authenticationManager：
     *      认证管理器当选择了资源所有者密码（password）授权类型的时候，需要设置这个属性注入一个 AuthenticationManager 对象。
     * userDetailsService：
     *      用户信息管理（不是客户端信息）设置了这个属性的话，那说明有一个自己的 UserDetailsService 接口的实现，当你设置了这个之后，
     *      那么 "refresh_token" 即刷新令牌授权类型模式的流程中就会包含一个检查，用来确保这个账号是否仍然有效，
     *      假如说禁用了这个账户的话。
     * authorizationCodeServices：
     *      这个属性是用来设置授权码服务的（即 AuthorizationCodeServices 的实例对象），主要用于 "authorization_code" 授权码类型模式。
     * implicitGrantService：
     *      这个属性用于设置隐式授权模式，用来管理隐式授权模式的状态。
     * tokenGranter：
     *      这个属性就很牛B了，当设置了这个东西（即 TokenGranter 接口实现），那么授权将会交由你来完全掌控，
     *      并且会忽略掉上面的这几个属性，这个属性一般是用作拓展用途的，即标准的四种授权模式已经满足不了你的需求的时候，
     *      才会考虑使用这个。
     *
     *
     *
     *
     *
     *
     *
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        /*
         * endpoints.pathMapping
         *      /oauth/authorize：授权端点。
         *      /oauth/token：令牌端点。 使用的是基于 HTTP Basic Authentication 标准
         *      /oauth/confirm_access：用户确认授权提交端点。
         *      /oauth/error：授权服务错误信息端点。
         *      /oauth/check_token：用于资源服务访问的令牌解析端点。
         *      /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
         *      以上是默认的路径，如果需要自定义这些路径，可以使用该方法：
         *          第一个参数：String 类型的，这个端点URL的默认链接。
         *          第二个参数：String 类型的，你要进行替代的URL链接。
         * 需要注意的是，这些url是被 security 保护起来的
         */
        endpoints.pathMapping("/oauth/token","/oauth/token");

        /*
         * oauth2 中认证有两个，其中一个就是 user 信息验证，这里就是配置自定义的 user 信息
         */
        endpoints.userDetailsService(userDetailService);

        /*
         * 在使用客户端模式认证的时候，必须设置一个 认证Manager
         * 否则报错：
         *      "error": "unsupported_grant_type",
         *       "error_description": "Unsupported grant type: password"
         */
        endpoints.authenticationManager(authenticationManager);

        // 很奇怪，配置 client 信息应该在上面那个方法中，为什么这里也可以设置？
        // 测试下来，客户端信息还是要在上面配置，如果上面不配置，这里配置，就会报错
//        endpoints.setClientDetailsService();





    }

    private ClientCredentialsTokenEndpointFilter checkTokenEndpointFilter = new ClientCredentialsTokenEndpointFilter();
    /**
     * 配置权限管理，oauth不需要认证，token检查需要授权
     * 访问token接口的安全限制,如tokenKeyAccess,checkTokenAccess
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(checkTokenEndpointFilter);
    }

}