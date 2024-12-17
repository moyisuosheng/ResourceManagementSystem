package com.myss.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 文件服务
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.myss.**.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.myss.*.api")
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}

