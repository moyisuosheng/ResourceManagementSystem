package com.myss.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * Minio配置属性类
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@ConfigurationProperties(prefix = "minio")  // 文件上传 配置前缀
@Configuration
@Data
public class MinioConfig implements Serializable {


    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 秘密密钥
     */
    private String secretKey;

    /**
     * 桶
     */
    private String bucket;

    /**
     * 端点
     */
    private String endpoint;

    /**
     * 域
     */
    private String domain;
}