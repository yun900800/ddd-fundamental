package org.ddd.fundamental.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //以下注释配置匿名访问,如果给某些url设置匿名权限就能通过外部访问
                //比如这里的url/security/customer
//                .anonymous()
//                .principal("anonymousUser")
//                .authorities("ROLE_ANONYMOUS")
//                .and()
                .authorizeRequests()
                .antMatchers("/security/customer").hasAnyAuthority("ROLE_CUSTOMER") // (1)
                //.antMatchers("/security/customer").hasAnyAuthority("ROLE_CUSTOMER","ROLE_ANONYMOUS") // (1 has ROLE_ANONYMOUS)
                .antMatchers("/security/call-center").hasAnyAuthority("ROLE_ADMIN", "ROLE_CALL-CENTER") // (2)
                .antMatchers("/security/admin").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated() // (3)
                .and()
                .formLogin()
                .and()
                .httpBasic();
        //super.configure(http);
    }
}
