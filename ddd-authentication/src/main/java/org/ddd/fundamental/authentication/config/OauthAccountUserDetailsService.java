package org.ddd.fundamental.authentication.config;

import org.ddd.fundamental.authentication.domain.entity.OauthAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class OauthAccountUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthAccountUserDetailsService.class);

    private final BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    @Resource
    private ClientDetailsService clientDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String clientId;
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof User) {
//                User clientUser = (User) principal;
//                clientId = clientUser.getUsername();
//            } else if (principal instanceof OauthAccountUserDetails) {
//                getClientIdByRequest();
//                return (OauthAccountUserDetails) principal;
//            } else {
//                throw new UnsupportedOperationException();
//            }
//        } else {
//            clientId = getClientIdByRequest();
//        }
        // 获取用户
        OauthAccount account = new OauthAccount();
        account.setUsername("admin");
        account.setPassword(passwordEncoder.encode("password"));
        //account.setPassword("admin");
        account.setClientId("admin");
        account.setAccountNonDeleted(true);
        account.setAccountNonLocked(true);
        account.setAccountNonExpired(true);
        account.setCredentialsNonExpired(true);
        account.setEnabled(true);
        LOGGER.info("account:{}",account);
        // 用户不存在
        if (account == null || !account.getAccountNonDeleted()) {
            throw new UsernameNotFoundException("user not found");
        }
        LOGGER.info("hello");
        // 授权
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new OauthAccountUserDetails(account, authorities);
    }

    /**
     * 从httpRequest中获取并验证客户端信息
     */
    public String getClientIdByRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) throw new UnsupportedOperationException();
        HttpServletRequest request = attributes.getRequest();
        UsernamePasswordAuthenticationToken client = authenticationConverter.convert(request);
        if (client == null) {
            throw new UnauthorizedClientException("unauthorized client");
        }
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(client.getName());
        if (!passwordEncoder.matches((String) client.getCredentials(), clientDetails.getClientSecret())) {
            throw new BadClientCredentialsException();
        }
        return clientDetails.getClientId();
    }
}
