package com.myss.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc配置文件
 * 访问：http://localhost:port/${context-path}/swagger-ui/index.html
 * 或者根据
 * springdoc.swagger-ui.path
 * 访问：http://localhost:port/${context-path}/doc.html
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Configuration
public class SpringDocConfig {
    /**
     * 开放API
     *
     * @param springDocsProperties Spring Docs 属性
     * @return {@link OpenAPI}
     */
    @Autowired
    @Bean
    public OpenAPI openApi(SpringDocsProperties springDocsProperties) {
        return new OpenAPI()
                // 文档描述信息
                .info(new Info()
                        .title(springDocsProperties.getTitle())
                        .description(springDocsProperties.getDescription())
                        .version(springDocsProperties.getVersion())
                )
                // 添加全局的header参数
                .addSecurityItem(new SecurityRequirement()
                        .addList(springDocsProperties.getHeader()))
                .components(new Components()
                        .addSecuritySchemes(springDocsProperties.getHeader(), new SecurityScheme()
                                .name(springDocsProperties.getHeader())
                                .scheme(springDocsProperties.getScheme())
                                .bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP))
                );
    }
}
