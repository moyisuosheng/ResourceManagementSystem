package com.myss.commons.model;


import com.myss.commons.enums.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 通用的结果返回类
 *
 * @param <T>
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Tag(name = "通用的结果返回类")
@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 958295628567280402L;
    /**
     * 正常返回码 100000
     */
    @Schema(defaultValue = "编码")
    private Integer code;
    /**
     * 错误提示
     */
    @Schema(defaultValue = "信息")
    private String msg;
    /**
     * 返回数据
     */
    @Schema(defaultValue = "数据")
    private T data;

    public ResponseResult() {
        this.code = ResultCode.SUCCESS.getCode();
//        this.msg = ResultCode.SUCCESS.getDesc();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();

        this.msg = resultCode.getDesc();

        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseResult<T> okResult() {
        return okResult(null);
    }

    public static <T> ResponseResult<T> okResult(T data) {
        return new ResponseResult<T>(ResultCode.SUCCESS, data);
    }

    public static <T> ResponseResult<T> errorResult(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> errorResult(ResultCode resultCode) {
        return new ResponseResult<>(resultCode.getCode(), resultCode.getDesc());
    }

    public static <T> ResponseResult<T> errorResult(ResultCode resultCode, String resultMessage) {
        return new ResponseResult<>(resultCode.getCode(), resultMessage);
    }

}
