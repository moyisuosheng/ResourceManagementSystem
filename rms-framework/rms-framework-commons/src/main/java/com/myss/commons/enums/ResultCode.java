package com.myss.commons.enums;

import lombok.Getter;

/**
 * 响应枚举
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Getter
public enum ResultCode {

    SUCCESS(100000, "ok"),

    LOGIN_ERROR(1001, "用户名或者密码错误"),

    PARAMETER_ERROR(9997, "参数错误"),

    DATA_NOT_DELETE(9996, "此数据不可删除"),

    DATA_NOT_MODIFIED(9995, "此数据不可修改"),

    VERIFY_ERROR(7009, "验证码错误"),

    DATA_SAVE_FAILED(9994, "数据保存失败"),

    INCONSISTENT(7477, "密码输入不一致"),

    DATA_ERROR(9993, "数据不合法"),

    NOT_FOUND(9991, "数据不存在"),

    NOT_TOKEN(9989, "token不合法"),

    DATA_ILLEGAL(8001, "数据状态不合法"),

    /**
     * 文件太大
     */
    FILE_TOO_LARGE(5554, "文件太大"),

    /**
     * 文件已存在
     */
    FILE_ALREADY_EXIST(5555, "文件已存在"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED(5556, "文件上传失败"),
    /**
     * 服务器内部错误
     */
    ERROR(9999, "服务器内部错误，请稍后再试");

    /**
     * 代码
     */
    private final int code;

    /**
     * 描述
     */
    private final String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
