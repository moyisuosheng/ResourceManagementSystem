package com.myss.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     *
     */
    @TableField(value = "password")
    private String password;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private String sex;

    /**
     * 身份证号
     */
    @TableField(value = "id_number")
    private String idNumber;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 状态 0:禁用;1:正常
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;

    /**
     *
     */
    @TableField(value = "create_user")
    private String createUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 2L;
}