package com.myss.commons.model;


import com.myss.commons.model.dto.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * 通用分页结果
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Tag(name = "通用的分页结果返回类")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageResponseResult<T extends Collection> extends ResponseResult<T> implements Serializable {

    /**
     * 页码
     */
    @Schema(defaultValue = "当前页")
    private Long pageNumber;
    /**
     * 页面大小
     */
    @Schema(defaultValue = "每页条数")
    private Integer pageSize;
    /**
     * 总条目
     */
    @Schema(defaultValue = "总数")
    private Long total;

    /**
     * 页面响应结果
     *
     * @param pageNumber 页码
     * @param pageSize   页面大小
     * @param total      总条目
     */
    public PageResponseResult(Long pageNumber, Integer pageSize, Long total) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * 页面响应结果
     *
     * @param pageNumber 页码
     * @param pageSize   大小
     * @param total      总
     * @param data       数据
     */
    public PageResponseResult(Long pageNumber, Integer pageSize, Long total, T data) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
        this.setData(data);
    }

    /**
     * 页面响应结果
     *
     * @param pageParam 分页参数对象
     * @param data      数据
     */
    public PageResponseResult(PageParam pageParam, T data) {
        if (data == null) {
            throw new NullPointerException();
        }
        this.pageNumber = (long) pageParam.getPageNumber();
        this.pageSize = pageParam.getPageSize();
        this.total = (long) data.size();
        this.setData(data);
    }

}
