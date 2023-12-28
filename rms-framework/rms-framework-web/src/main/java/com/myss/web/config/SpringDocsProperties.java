package com.myss.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Spring Docs 属性
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "spring-docs")
public class SpringDocsProperties {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 版本
     */
    private String version;
    /**
     * 页眉
     */
    private String header;
    /**
     * 方案
     */
    private String scheme;
}
