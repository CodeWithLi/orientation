package com.example.orientation.interceptor;




import com.example.orientation.constant.JwtClaimsConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.properties.JwtProperties;
import com.example.orientation.utils.BaseUtils;
import com.example.orientation.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


import java.util.Enumeration;


/**
 * 拦截器
 * 拦截前端传来的token并解析存放用户信息到线程中
 */
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtProperties jwtProperties;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String account=null;
//判断是否为跨域
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            log.info("当前请求为跨域，放行请求");
            return true;
        }
//获取前端传来的token值，判断是否可以正常操作
        String tokenHeader = request.getHeader(jwtProperties.getOrTokenName());
        if (!StringUtils.hasText(tokenHeader)){
            log.info("token值为:{}",tokenHeader);
            throw new BaseException("操作失败，拦截器获取token值为空");
        }
        log.info("拦截器获取到token值为：{}",tokenHeader);
//解析token值
        Claims claims = JwtUtils.praseJwt(jwtProperties.getOrSecretKey(), tokenHeader);
//判断为用户操作还是管理员操作
        String uri = request.getRequestURI();
//为后台管理员操作
        if (uri.contains("/admin")) {
            account = String.valueOf(claims.get(JwtClaimsConstant.Admin_account));
        }else if (uri.contains("/mobile")){
            account = String.valueOf(claims.get(JwtClaimsConstant.User_Account));
        }
        if (!StringUtils.hasText(account)||"null".equalsIgnoreCase(account)){
            throw new BaseException("拦截器获取的token值中无信息");
        }
//存储用户信息到线程中，方便调用
        BaseUtils.setCurrentAccount(account);
        return true;
    }
}
