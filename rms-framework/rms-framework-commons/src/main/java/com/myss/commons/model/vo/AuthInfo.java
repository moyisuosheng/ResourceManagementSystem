package com.myss.commons.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 存储token中的用户信息
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */


@Data
public class AuthInfo {

    /**
     * 用户id
     */
    private Long uid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 公司名称
     */
    private String companyName;
}
