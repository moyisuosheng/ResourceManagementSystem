package com.myss.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurity 5.4.x以上新用法配置
 * 为避免循环依赖，仅用于配置HttpSecurity
 * Created by macro on 2022/5/19.
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * 过滤链
     *
     * @param http HTTP协议
     * @return {@link SecurityFilterChain}
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
          链式编程
          首页所有人都可以访问，功能也只有对应有权限的人才能访问到
          请求授权的规则
         */
        return http
                .authorizeRequests()
                //首页所有人可以访问
                .antMatchers("**/ping").permitAll()
                //特定权限访问页
                .antMatchers("/swagger-ui/**", "/doc.html", "/v3/api-docs/**").hasRole("doc")
                .and()
                //无权限默认跳转登录页
                .formLogin()
                .and()
                //注销，开启注销功能，跳转到首页
                .logout().logoutSuccessUrl("/")
                .and()
                .build();
    }

    /**
     * 用户认证服务
     * 账号：user；密码：123456
     * @return {@link UserDetailsService}
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //密码加密
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        //设置用户
        UserDetails docUser = User.builder()
                .username("user")
                .password(encoder.encode("123456"))
                //用户权限
                .roles("doc")
                .build();
        return new InMemoryUserDetailsManager(docUser);
    }
}
