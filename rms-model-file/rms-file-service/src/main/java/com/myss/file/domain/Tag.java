package com.myss.file.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName tag
 */
@TableName(value = "tag")
@Data
public class Tag implements Serializable {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标签名称
     */
    @TableField(value = "tag_name")
    private String tagName;

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
    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;

    /**
     *
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}