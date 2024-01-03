package org.ddd.fundamental.authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthorizationServerTokenServices tokenServices;
    @Resource
    private AuthenticationManager authenticationManagerBean;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //clients.withClientDetails(jdbcClientDetailsService);
        clients.inMemory()
                //配置client_id
                .withClient("admin")
                //配置client-secret
                .secret(passwordEncoder.encode("password"))
                //配置访问token的有效期
                .accessTokenValiditySeconds(3600)
                //配置刷新token的有效期
                .refreshTokenValiditySeconds(864000)
                //配置redirect_uri，用于授权成功后跳转
                .redirectUris("http://localhost:9021/login")
                //配置申请的权限范围
                .scopes("all")
                /**
                 * 配置grant_type，表示授权类型
                 * authorization_code: 授权码模式
                 * implicit: 简化模式
                 * password： 密码模式
                 * client_credentials: 客户端模式
                 * refresh_token: 更新令牌
                 */
                .authorizedGrantTypes("authorization_code","implicit","password","client_credentials","refresh_token");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) {
        configurer.tokenServices(tokenServices);
        configurer.authenticationManager(authenticationManagerBean);
    }
}
