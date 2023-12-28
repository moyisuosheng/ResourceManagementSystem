package com.myss.file.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myss.commons.enums.ResultCode;
import com.myss.commons.model.vo.flie.FileVo;
import com.myss.commons.utils.BeanHelper;
import com.myss.search.api.SearchClient;
import com.myss.file.domain.File;
import com.myss.file.service.FileService;
import com.myss.file.service.FileUploadService;
import com.myss.file.util.MinioTemplate;
import com.myss.web.exception.CustomException;
import com.twmacinta.util.MD5;
import com.twmacinta.util.MD5InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传服务实现
 *
 * @author moyis
 * @date 2023/04/25
 */
@Service
@Slf4j
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

    /**
     * minio工具类
     */
    @Autowired
    private MinioTemplate minioTemplate;

    @Autowired
    private FileService fileService;

    @Autowired
    private SearchClient searchClient;

    /**
     * 计算文件MD5校验并上传
     *
     * @param uploadFile 上传文件
     * @return {@link String}
     * @throws IOException ioexception
     */
    @Override
    public String verifyMd5AndUpload(MultipartFile uploadFile) throws Exception {
        try (InputStream inputStream = uploadFile.getInputStream();) {
            long startTime = System.currentTimeMillis();
            String hash = MD5.asHex(new MD5InputStream(inputStream).hash());
            long endTime = System.currentTimeMillis();

            log.error("hash:{},startTime:{},endTime:{},{}:秒", hash, startTime, endTime, ((double) (endTime - startTime)) / 1000);

            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getFileIdentifier, hash);
            long count = fileService.count(fileLambdaQueryWrapper);
            if (count != 0) {
                throw new CustomException(ResultCode.FILE_ALREADY_EXIST);
            }

            // 上传文件到minio,并获取返回值
            try (InputStream uploadFileInputStream = uploadFile.getInputStream();) {
                String fileUrl = minioTemplate.upload(uploadFileInputStream,
                        uploadFile.getOriginalFilename(),
                        uploadFile.getContentType());

                File file = new File();
                file.setFileIdentifier(hash);
                file.setFileName(uploadFile.getOriginalFilename());
                file.setFileUrl(fileUrl);
                file.setFileType(uploadFile.getContentType());
                fileService.save(file);
                searchClient.add(BeanHelper.copyProperties(file, FileVo.class));
                return fileUrl;
            }
        }
    }

    /**
     * 仅上传
     *
     * @param uploadFile 上传文件
     * @return {@link String}
     * @throws IOException ioexception
     */
    @Override
    public String upload(MultipartFile uploadFile, String hash) throws Exception {

        LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.eq(File::getFileIdentifier, hash);
        long count = fileService.count(fileLambdaQueryWrapper);
        if (count != 0) {
            throw new CustomException(ResultCode.FILE_ALREADY_EXIST);
        }
        // 上传文件到minio,并获取返回值
        try (InputStream uploadFileInputStream = uploadFile.getInputStream();) {

            String fileUrl = minioTemplate.upload(uploadFileInputStream,
                    uploadFile.getOriginalFilename(),
                    uploadFile.getContentType());

            File file = new File();
            file.setFileIdentifier(hash);
            file.setFileName(uploadFile.getOriginalFilename());
            file.setFileUrl(fileUrl);
            file.setFileType(uploadFile.getContentType());
            fileService.save(file);
            searchClient.add(BeanHelper.copyProperties(file, FileVo.class));
            return fileUrl;
        }

    }



}
