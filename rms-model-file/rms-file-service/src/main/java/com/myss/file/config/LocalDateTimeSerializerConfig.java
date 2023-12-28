package com.myss.file.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 当地日期时间序列化器配置
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Configuration
@Getter
public class LocalDateTimeSerializerConfig {
    @Value("${spring.jackson.dateFormat}")
    private String pattern;
}