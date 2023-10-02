package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginFilterCheckFilter", urlPatterns = "/*")
public class LoginFilterChecker implements Filter {
    
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        String[] urls = new String[] {
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**"
        };
        log.info("拦截网络请求：{}",request.getRequestURI());
        boolean check = check(urls,requestURI);
        //如果不需要登录
        if(check) {
            log.info("本次请求不需要处理:{}",request.getRequestURI());
            filterChain.doFilter(request,response);
            return;
        }
        //已登录
        if(request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOT LOGIN")));
    }

    public  boolean check(String[] urls, String requestURI) {
        for (String url: urls) {
           boolean match = PATH_MATCHER.match(url,requestURI);
           if(match) {
               return true;
           }
        }
        return false;

    }
}
