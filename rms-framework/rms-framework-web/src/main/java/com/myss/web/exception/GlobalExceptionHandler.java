package com.myss.web.exception;



import com.myss.commons.enums.ResultCode;
import com.myss.commons.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;


/**
 * 全局异常处理器
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */
@RestControllerAdvice
//        (basePackages = {
////        "com.myss.**.controller"
////        "com.myss.video.controller",
////        "com.myss.system.controller",
////        "com.myss.search.controller",
//})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数统一校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult<String> paramExceptionHandler(HttpMessageNotReadableException e) {
        return ResponseResult.errorResult(ResultCode.PARAMETER_ERROR, e.getMessage());
    }


    @ExceptionHandler(value = MultipartException.class)
    public ResponseResult<String> fileUploadExceptionHandler(MultipartException exception) {

        Throwable rootCause = exception.getRootCause();
        if (rootCause instanceof MaxUploadSizeExceededException) {

            return ResponseResult.errorResult(ResultCode.FILE_TOO_LARGE, exception.getMessage());
        }
        return ResponseResult.errorResult(ResultCode.ERROR);
    }

    // 预期异常
    @ExceptionHandler(CustomException.class)
    public ResponseResult<String> customException(CustomException e) {
        return ResponseResult.errorResult(e.getResultCode());
    }

    // 非预期异常
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> exception(Exception e) {
        log.error(e.getMessage());
        return ResponseResult.errorResult(ResultCode.ERROR);
    }
}
