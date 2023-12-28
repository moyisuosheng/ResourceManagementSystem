package com.myss.file.controller;


import com.myss.commons.model.ResponseResult;
import com.myss.file.mapper.FileMapper;
import com.myss.file.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文件上传控制器
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Tag(name = "文件上传")
@Validated
@RestController
@RequestMapping("/v1/public/file/")
@Slf4j
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 计算文件MD5校验并上传
     *
     * @param uploadFile 上传文件
     * @return {@link ResponseResult}<{@link String}>
     * @throws IOException ioexception
     */
    @Operation(summary = "计算文件MD5校验并上传")
//    @ApiImplicitParam(name = "uploadFile",value = "上传签名文件",required = true,dataTypeClass= MultipartFile.class,allowMultiple = true,paramType = "query")
    @Parameter(name = "uploadFile", description = "上传的文件", required = true)
    @PostMapping("/verify/upload")
    public ResponseResult<String> verifyMd5AndUploadAll(@RequestPart @RequestParam(value = "uploadFile") MultipartFile uploadFile) throws Exception {
        return ResponseResult.okResult(fileUploadService.verifyMd5AndUpload(uploadFile));
    }

    /**
     * 携带Md5上传
     *
     * @param uploadFile 上传文件
     * @return {@link ResponseResult}<{@link String}>
     * @throws IOException ioexception
     */
    @Operation(summary = "携带Md5上传")
//    @ApiImplicitParam(name = "uploadFile",value = "上传签名文件",required = true,dataTypeClass= MultipartFile.class,allowMultiple = true,paramType = "query")
    @Parameters({
            @Parameter(name = "uploadFile", description = "上传的文件", required = true),
            @Parameter(name = "hash", description = "文件的Md5", required = true)
    })
    @PostMapping("upload")
    public ResponseResult<String> uploadAll(@RequestPart @RequestParam(value = "uploadFile") MultipartFile uploadFile
            , @NonNull @RequestParam(value = "hash") String hash) throws Exception {
        return ResponseResult.okResult(fileUploadService.upload(uploadFile, hash));
    }

    /**
     * 验证文件
     *
     * @param HashList 哈希列表
     * @return {@link ResponseResult}
     * @throws Exception 例外
     */
    @PostMapping("/verify/files")
    public ResponseResult verifyFiles(@RequestBody List<String> HashList) throws Exception {
        Map map = fileMapper.verifyFiles(HashList);
        return ResponseResult.okResult(map);
    }

    /**
     * ping
     *
     * @return {@link ResponseResult}
     * @throws Exception 例外
     */
    @GetMapping("/verify/ping")
    public ResponseResult ping() throws Exception {

        return ResponseResult.okResult(fileMapper.ping());
    }
}
