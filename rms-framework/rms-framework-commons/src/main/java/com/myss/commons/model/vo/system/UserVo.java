package com.myss.commons.model.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/27
 */
@Schema(name = "用户信息Vo")
@Data
public class UserVo implements Serializable {

    /**
     * 串口版 UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电话
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 名字
     */
    private String name;

    /**
     * 公司 ID
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 用户图片
     */
    private String userPic;

    /**
     * 用户类型
     */
    private String usertype;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别
     */
    private String gender;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 创建日期
     */
    private Date createDate;


}
