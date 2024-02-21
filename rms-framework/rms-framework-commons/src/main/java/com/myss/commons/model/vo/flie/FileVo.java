package com.myss.commons.model.vo.flie;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 文件信息
 *
 * @TableName file
 */
@Schema(name = "文件信息Vo")
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class FileVo implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "[主键]不能为空")
    @Schema(defaultValue = "主键")
    private Long id;
    /**
     * 文件名
     */
    @NotBlank(message = "[文件名]不能为空")
    @Size(max = 256, message = "编码长度不能超过256")
    @Schema(defaultValue = "文件名")
    @Length(max = 256, message = "编码长度不能超过256")
    private String fileName;
    /**
     * 文件地址
     */
    @NotBlank(message = "文件地址不可为空", groups = {FileVo.Save.class})
    @NotBlank(message = "[文件地址]不能为空")
    @Size(max = 1024, message = "编码长度不能超过1024")
    @Schema(defaultValue = "文件地址")
    @Length(max = 1024, message = "编码长度不能超过1,024")
    private String fileUrl;
    /**
     * 标识符
     */
    @NotBlank(message = "[标识符]不能为空")
    @Size(max = 32, message = "编码长度不能超过32")
    @Schema(defaultValue = "标识符")
    @Length(max = 32, message = "编码长度不能超过32")
    private String fileIdentifier;
    /**
     * 文件类型
     */
    @Size(max = 32, message = "编码长度不能超过32")
    @Schema(defaultValue = "文件类型")
    @Length(max = 32, message = "编码长度不能超过32")
    private String fileType;
    /**
     * 状态 0:禁用;1:正常
     */
    @Schema(defaultValue = "状态 0:禁用;1:正常")
    private Integer status;
    /**
     * 更新时间
     */
    @Schema(defaultValue = "更新时间")
    private Long updateTime;
    /**
     * 更新人
     */
    @Size(max = 32, message = "编码长度不能超过32")
    @Schema(defaultValue = "更新人")
    @Length(max = 32, message = "编码长度不能超过32")
    private String updateUser;
    /**
     * 创建时间
     */
    @Schema(defaultValue = "创建时间")
    private Long createTime;
    /**
     * 创建人
     */
    @Size(max = 32, message = "编码长度不能超过32")
    @Schema(defaultValue = "创建人")
    @Length(max = 32, message = "编码长度不能超过32")
    private String createUser;


    public interface Save {

    }
}
