oauth2 四种授权方式
    授权码模式（authorization code）
    简化模式（implicit）
    密码模式（resource owner password credentials）
    客户端模式（client credentials）
        指客户端以自己的名义，而不是以用户的名义，向"服务提供商"进行认证。严格地说，客户端模式并不属于OAuth框架所要解决的问题


密码模式默认是不支持的，如果需要使用，要注入一个 authenticationManager
登录方式：
    URL -> http://localhost:8080/oauth/token?username=user&password=123456&grant_type=password&scope=all
    客户端信息使用 Basic Auth 方式
        请求头两个：
            Content-Type=application/x-www-form-urlencoded
            Authorization=Basic Y2xpZW50OjEyMzQ1Ng==
                Basic后面字符串就是 clientName 和 clientSecret 加密的结果

这种模式一般是客户端和认证中心是同一个机构的，极度信任状态


















