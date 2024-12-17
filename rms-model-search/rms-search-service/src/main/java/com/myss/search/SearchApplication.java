package com.myss.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author moyis
 * @date 2023/11/823:22
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.myss.*.mapper"})
// 指定启动类包扫描范围
@ComponentScan(basePackages = {"com.myss.*", "com.myss.web.**", "com.myss.mybatis", "com.myss.*.api"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.myss.*.api")
@EnableElasticsearchRepositories({"com.myss.*.repository"})
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
