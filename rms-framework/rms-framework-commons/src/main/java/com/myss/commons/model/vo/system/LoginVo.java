package com.myss.commons.model.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录 VO
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "登录Vo")
public class LoginVo {

    /**
     * 电话
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 访问令牌
     */
    private String accessToken;

}
