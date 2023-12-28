package com.myss.commons.model.dto;


import com.myss.commons.constants.Constants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 登录DTO
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/27
 */
@Data
public class LoginDto {

    /**
     * 电话
     */
    @NotBlank(message = "手机号码不能为空")
    @NotNull(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = Constants.Regexps.PHONE, message = "手机号格式有误")
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "密码大于6位小于32位")
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;


}
