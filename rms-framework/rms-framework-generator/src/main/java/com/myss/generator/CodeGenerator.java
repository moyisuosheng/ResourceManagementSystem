package com.myss.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {

    private static final String SERVICE_NAME = "system";


    //代码生成
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig());
        generator.packageInfo(packageConfig());
        generator.global(globalConfig());
        generator.template(templateConfig());
        generator.execute(new FreemarkerTemplateEngine());
    }

    //生成java代码的路径位置
    private static final String JAVA_PATH = System.getProperty("user.dir") + "/"
            + "rms-model-" + SERVICE_NAME + "/"
            + "rms-" + SERVICE_NAME + "-service" + "/"
            + "src/main/java";

    //生成SQL映射文件的路径位置
    private static final String XML_PATH = System.getProperty("user.dir") + "/"
            + "rms-model-" + SERVICE_NAME + "/"
            + "rms-" + SERVICE_NAME + "-service" + "/"
            + "src/main/resources/mappers/";

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://127.0.0.1:3306/rms-" + SERVICE_NAME + "?useUnicode=true&useSSL=false&characterEncoding=utf8"
            , "root", "root").build();

    /**
     * 策略配置
     */
    private static StrategyConfig strategyConfig() {
        StrategyConfig config = new StrategyConfig.Builder()
                .addExclude("undo_log") // 设置需要排除生成的表名
                .addTablePrefix("tb_")  // 排除表名的tb_前缀
                .build();
        mapperBuilder(config);
        entityBuilder(config);
        controllerBuilder(config);
        serviceBuilder(config);
        return config;
    }

    //指定dao接口的生成策略
    private static StrategyConfig mapperBuilder(StrategyConfig config) {
        return config.mapperBuilder()
                .build();
    }

    //指定实体类的生成策略
    private static StrategyConfig entityBuilder(StrategyConfig config) {
        return config.entityBuilder()
                .enableLombok()
                .idType(IdType.AUTO)
                .build();
    }

    //指定Controller的生成策略
    private static StrategyConfig controllerBuilder(StrategyConfig config) {
        return config.controllerBuilder()
                .enableRestStyle()
                .build();
    }

    //指定Service的生成策略
    private static StrategyConfig serviceBuilder(StrategyConfig config) {
        return config.serviceBuilder()
                .formatServiceFileName("%sService")
                .build();
    }

    /**
     * 全局配置
     */
    private static GlobalConfig globalConfig() {
        return new GlobalConfig.Builder()
                //.fileOverride()
                .disableOpenDir()
                .author("moyisuosheng")
                .dateType(DateType.ONLY_DATE)
                .outputDir(JAVA_PATH).build();
    }

    /**
     * 模板配置
     */
    private static TemplateConfig templateConfig() {
        return new TemplateConfig.Builder()
                .controller("/templates/controller.java").build();
    }

    /**
     * 包配置
     */
    private static PackageConfig packageConfig() {
        return new PackageConfig.Builder()
                .parent("com.myss." + SERVICE_NAME)
                .service("service")
                .mapper("mapper")
                .controller("controller")
                .entity("domain")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, XML_PATH)).build();
    }

}
