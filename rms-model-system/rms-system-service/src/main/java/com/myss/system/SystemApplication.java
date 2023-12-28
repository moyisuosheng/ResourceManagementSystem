package com.myss.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.myss.**.mapper") // 指定mapper包扫描范围
@ComponentScan(basePackages = {"com.myss.system","com.myss.web","com.myss.web.config","com.myss.mybatis"}) // 指定启动类包扫描范围
@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.myss.*.api")//指定启动类包扫描范围
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
