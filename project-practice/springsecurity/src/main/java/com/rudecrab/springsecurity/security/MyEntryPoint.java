package com.rudecrab.springsecurity.security;

import com.rudecrab.springsecurity.enums.ResultCode;
import com.rudecrab.springsecurity.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证错误处理器
 *
 * @author RudeCrab
 */
@Slf4j
public class MyEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error(e.getMessage());
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ResultVO<String> resultVO = new ResultVO<>(ResultCode.UNAUTHORIZED, "没有登录");
        out.write(resultVO.toString());
        out.flush();
        out.close();
    }
}
