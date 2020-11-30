package com.rudecrab.springsecurity.security;

import com.rudecrab.springsecurity.serviceimpl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过滤器
 *
 * @author RudeCrab
 */
@Slf4j
@Component
public class LoginFilter extends OncePerRequestFilter {
    @Autowired
    private JwtManager jwtManager;
    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("---LoginFilter---");
        // 从请求头中获取token字符串并解析
        Claims claims = jwtManager.parse(request.getHeader("Authorization"));
        if (claims != null) {
            String username = claims.getSubject();
            UserDetails user = userService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
