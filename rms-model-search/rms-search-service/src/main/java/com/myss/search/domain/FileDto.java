package com.myss.search.domain;

import com.myss.commons.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 文件 DTO
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = Constants.IndexNames.FILE, shards = 3, replicas = 1)
public class FileDto {
    /**
     * 编号
     */
    @Id
    private Long id;//商品唯一标识

    /**
     * 文件名
     */
    @Field(type = FieldType.Text, analyzer = Constants.Analyzer.IK_SMART)
    private String fileName;

    /**
     * 文件 URL
     */
    @Field(type = FieldType.Keyword, index = false)
    private String fileUrl;

    /**
     * 文件类型
     */
    @Field(type = FieldType.Keyword)
    private String fileType;

    /**
     * 文件标识符
     */
    @Field(type = FieldType.Keyword)
    private String fileIdentifier;

    /**
     * 状态
     */
    @Field(type = FieldType.Keyword)
    private String status;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Long)
    private Long updateTime;

    /**
     * 更新用户
     */
    @Field(type = FieldType.Keyword)
    private String updateUser;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Long)
    private Long createTime;

    /**
     * 创建用户
     */
    @Field(type = FieldType.Keyword)
    private String createUser;
}
