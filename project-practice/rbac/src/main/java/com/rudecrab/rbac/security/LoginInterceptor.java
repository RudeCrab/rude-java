package com.rudecrab.rbac.security;

import com.rudecrab.rbac.enums.ResultCode;
import com.rudecrab.rbac.exception.ApiException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RudeCrab
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtManager jwtManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token字符串并解析
        Claims claims = jwtManager.parse(request.getHeader("Authorization"));
        // 已登录就直接放行
        if (claims != null) {
            // 将我们之前放到token中的userId给存到上下文对象中
            UserContext.add(Long.parseLong(claims.getSubject()));
            return true;
        }

        // 走到这代表没有登录
        throw new ApiException(ResultCode.UNAUTHORIZED);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束后要从上下文对象删除数据，如果不删除则可能会导致内存泄露
        UserContext.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
