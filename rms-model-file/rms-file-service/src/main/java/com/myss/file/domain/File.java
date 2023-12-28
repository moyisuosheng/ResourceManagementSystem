package com.myss.file.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件信息
 *
 * @TableName file
 */
@TableName(value = "file")
@Data
public class File implements Serializable {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件名
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件地址
     */
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 标识符
     */
    @TableField(value = "file_identifier")
    private String fileIdentifier;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private String fileType;

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
     * 创建人
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}