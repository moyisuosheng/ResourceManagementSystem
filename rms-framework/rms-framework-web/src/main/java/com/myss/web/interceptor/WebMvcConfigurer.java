//package com.myss.web.interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * Web MVC 配置器
// *
// * @author zhurongxu
// * @version 1.0.0
// * @date 2023/12/28
// */
//@Configuration
//public class WebMvcConfigurer extends WebMvcConfigurationSupport {
//
////    /**
////     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
////     * 解决引入springDoc后启动失败的问题
////     */
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/**").addResourceLocations(
////                "classpath:/static/");
//////        registry.addResourceHandler("swagger-ui.html", "doc.html").addResourceLocations(
//////                "classpath:/META-INF/resources/");
////        registry.addResourceHandler("/webjars/**").addResourceLocations(
////                "classpath:/META-INF/resources/webjars/");
////        super.addResourceHandlers(registry);
////    }
//
//
//    /**
//     * 配置静态资源,避免静态资源请求被拦截
//     *
//     * @param registry 注册表
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/templates/**")
//                .addResourceLocations("classpath:/templates/");
//        registry.addResourceHandler("swagger-ui.html", "doc.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        super.addResourceHandlers(registry);
//    }
//
//
//}
