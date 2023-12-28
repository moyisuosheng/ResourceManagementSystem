package com.myss.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author moyis
 * @title: VideoApplication
 * @projectName ResourceManagementSystem
 * @description: TODO
 * @date 2023/11/823:22
 */
@SpringBootApplication
@EnableTransactionManagement

@MapperScan({"com.myss.*.mapper"})
@ComponentScan(basePackages = {"com.myss.search","com.myss.web.**","com.myss.mybatis","com.myss.*.api"}) // 指定启动类包扫描范围

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.myss.*.api")
public class SearchApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SearchApplication.class, args);
    }
}
