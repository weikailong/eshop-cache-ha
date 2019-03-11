package com.kaixin.eshop.cache.ha.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @description: hystrix请求上下文过滤器
 * @author: weikailong on 2019-03-11 13:12
 **/
public class HystrixRequestContextFilter implements Filter {


    @Override
    public void init(FilterConfig config) throws ServletException {
         
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse  response, 
                         FilterChain chain) throws IOException, ServletException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.shutdown();
        }
    }

    @Override
    public void destroy() {

    }
}
