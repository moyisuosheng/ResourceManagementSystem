package com.myss.commons.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 分页参数
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Data
public class PageParam {
    /**
     * 页码
     */
    @Min(value = 0, message = "页码不能小于0")
    private Integer pageNumber;
    /**
     * 页面大小
     */
    @Min(value = 1, message = "单页数据量不能小于1")
    private Integer pageSize;
    /**
     * 顺序
     */
    private String order;
    /**
     * 排序方式
     */
    private String sort;
}
