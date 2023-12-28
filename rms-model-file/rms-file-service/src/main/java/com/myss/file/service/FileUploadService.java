package com.myss.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传服务
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
public interface FileUploadService {

    /**
     * 计算文件MD5校验并上传
     *
     * @param uploadFile 上传文件
     * @return {@link String}
     * @throws IOException ioexception
     */
    String verifyMd5AndUpload(MultipartFile uploadFile) throws Exception;

    /**
     * 上传
     *
     * @param uploadFile 上传文件
     * @param hash       散 列
     * @return {@link String}
     * @throws Exception 例外
     */
    String upload(MultipartFile uploadFile, String hash) throws Exception;


}
