package com.myss.file.api;

import com.myss.commons.model.ResponseResult;
import com.myss.commons.model.vo.flie.FileVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author moyis
 * @title: FileClient
 * @projectName ResourceManagementSystem
 * @description: 文件服务的远程调用api
 * @date 2023/12/1122:04
 */
@FeignClient(value = "rms-file-service", path = "/rms-file/v1/public/video")
public interface FileClient {
    @GetMapping("/{id}")
    public ResponseResult<FileVo> getVideoById(@PathVariable(value = "id") Long id);
}
