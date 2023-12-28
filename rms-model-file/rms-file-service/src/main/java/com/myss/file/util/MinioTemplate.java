package com.myss.file.util;

import cn.hutool.core.date.DateTime;
import com.myss.commons.enums.ResultCode;
import com.myss.file.config.MinioConfig;
import com.myss.web.exception.CustomException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.UUID;

/**
 * minio模板
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Component
public class MinioTemplate {

    // 声明MIME类型常量
    public static final String TEXT_HTML = "text/html";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String APPLICATION_PDF = "application/pdf";

    @Autowired
    private MinioConfig minioConfig;

    private MinioClient minioClient;

    // 初始化
    @PostConstruct
    public void init() {
        minioClient = MinioClient
                .builder()
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .endpoint(minioConfig.getEndpoint())
                .build();
    }

    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param fileName    文件名称
     * @param contentType 文件类型
     * @return {@link String}
     */ // 文件上传
    public String upload(InputStream inputStream, String fileName, String contentType) {
        try {
            // 处理上传文件的文件名称
            String filePath =
                    "all/" + DateTime.now().toString("yyyy/MM/dd/") + UUID.randomUUID()
                            + "-" + fileName;
            // 构造上传的参数对象PutObjectArgs
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket()) // 指定桶
                    .contentType(contentType) // 指定MIME类型
                    .object(filePath) // 指定文件名
                    .stream(inputStream, inputStream.available(), -1) // 指定文件流
                    .build();
            // 上传
            minioClient.putObject(args);
            // 获取文件的访问地址
            return minioConfig.getDomain() + "/"
                    + minioConfig.getBucket() + "/" + filePath;
        } catch (Exception e) {
            throw new CustomException(ResultCode.ERROR);
        }
    }

    /**
     * 上传html
     *
     * @param inputStream 输入流
     * @param fileName    文件名
     * @return {@link String}
     */
    public String uploadHtml(InputStream inputStream, String fileName) {
        return getUploadFilePathUrl(inputStream, fileName, TEXT_HTML);
    }

    /**
     * 获取上传文件路径url
     *
     * @param inputStream 输入流
     * @param fileName    文件名
     * @param fileType    文件类型
     * @return {@link String}
     */
    private String getUploadFilePathUrl(InputStream inputStream, String fileName, String fileType) {
        try {
            // 处理上传文件的文件名称
            String filePath = fileType.substring(fileType.indexOf("/") + 1) + "/" + DateTime.now()
                    .toString("yyyy/MM/dd/") + UUID.randomUUID().toString() + fileName;

            // 构造上传的参数对象PutObjectArgs
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket()) // 指定桶
                    .contentType(fileType) // 指定MIME类型
                    .object(filePath) // 指定文件名
                    .stream(inputStream, inputStream.available(), -1) // 指定文件流
                    .build();
            // 上传
            minioClient.putObject(args);
            // 获取文件的访问地址
            return minioConfig.getDomain() + "/" + minioConfig.getBucket() + "/" + filePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传pdf
     *
     * @param inputStream 输入流
     * @param filename    文件名
     * @return {@link String}
     */
    public String uploadPdf(InputStream inputStream, String filename) {
        return getUploadFilePathUrl(inputStream, filename, APPLICATION_PDF);
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名称
     */ //刪除
    public void delete(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(minioConfig.getBucket()).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}