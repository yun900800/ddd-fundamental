package org.ddd.fundamental.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明配置多种认证方式的话,只要一个认证通过那么全部通过
 */
public class CommonAuthenticationProvider implements AuthenticationProvider {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        LOGGER.info("userName:{},password:{}",userName, password);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (userName.equals("user")) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CUSTOMER");
            authorities.add(authority);
        } else if (userName.equals("admin")) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CUSTOMER");
            authorities.add(authority);
            SimpleGrantedAuthority authority1 = new SimpleGrantedAuthority("ROLE_ADMIN");
            authorities.add(authority1);
            SimpleGrantedAuthority authority2 = new SimpleGrantedAuthority("ROLE_CALL-CENTER");
            authorities.add(authority2);
        }
        return new UsernamePasswordAuthenticationToken(userName, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
