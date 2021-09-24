package com.tomhusky.wechatmvc.server.security;

import com.tomhusky.wechatmvc.server.service.UserService;
import com.tomhusky.wechatmvc.server.vo.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author : lwj
 * @Date: 2020-09-04 15:16
 * @Description : 用户信息加载服务
 */
@Service
public class AuthUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 实现了UserDetailsService接口中的loadUserByUsername方法
     * 执行登录,构建Authentication对象必须的信息,
     * 如果用户不存在，则抛出UsernameNotFoundException异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountInfo accountInfo = userService.getAccountInfoByName(username);
        if (accountInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("/*/**"));
        return new JwtUser(accountInfo, grantedAuthorities);
    }
}
