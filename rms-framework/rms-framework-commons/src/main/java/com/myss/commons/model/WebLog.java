package com.myss.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Controller层的日志封装类
 * Created by macro on 2018/4/26.
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
public class WebLog {
    /**
     * 描述
     */
    private String description;
    /**
     * 用户名
     */
    private String username;
    /**
     * 开始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 耗时
     */
    private Long spendTime;

    /**
     * 基本路径
     */
    private String basePath;
    /**
     * URI
     */
    private String uri;
    /**
     * 网址
     */
    private String url;
    /**
     * 方法
     */
    private String method;
    /**
     * ip
     */
    private String ip;
    /**
     * 参数
     */
    private Object parameter;
    /**
     * 结果
     */
    private Object result;

}
