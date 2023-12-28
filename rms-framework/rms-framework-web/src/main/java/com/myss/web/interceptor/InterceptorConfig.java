package com.myss.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc注册拦截器
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 身份验证拦截器
     */
    @Autowired
    private AuthInterceptor authInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
    }
}
