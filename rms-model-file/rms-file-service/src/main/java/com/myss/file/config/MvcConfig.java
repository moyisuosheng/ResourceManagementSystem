package com.myss.file.config;


import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.myss.web.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * mvc配置*-
 * 1..*
 * 133*
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Value("${spring.jackson.dateFormat}")
    private String pattern;

    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger**/**",
                        "/webjars/**",
                        "/v1/**",
                        "/doc.html");
    }

    /**
     * 自定义本地日期时间格式
     *
     * @return {@link Jackson2ObjectMapperBuilderCustomizer}
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeLocalDateTimeFormat() {
        return jacksonObjectMapperBuilder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer(formatter);
            LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(formatter);
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, serializer);
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, deserializer);
        };
    }
}
