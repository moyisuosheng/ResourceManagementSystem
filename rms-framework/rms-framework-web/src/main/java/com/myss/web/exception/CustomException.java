package com.myss.web.exception;

import com.myss.commons.enums.ResultCode;
import lombok.Data;


/**
 * 自定义业务异常
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */
@Data
public class CustomException extends RuntimeException {

    /**
     * 声明响应枚举（错误信息）
     */
    private  ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.getDesc());
        this.resultCode = resultCode;
    }
}
