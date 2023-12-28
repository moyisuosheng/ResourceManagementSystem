package com.myss.file.controller;


import cn.hutool.core.util.ObjectUtil;
import com.myss.commons.enums.ResultCode;
import com.myss.commons.model.ResponseResult;
import com.myss.commons.model.vo.flie.FileVo;
import com.myss.commons.utils.BeanHelper;
import com.myss.file.domain.File;
import com.myss.file.service.FileService;
import com.myss.web.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moyis
 * @title: VideoController
 * @projectName ResourceManagementSystem
 * @description: TODO
 * @date 2023/11/2814:27
 */
@Tag(name = "视频类")
@Controller
@RestController
@RequestMapping("/v1/public/video")
@Slf4j
public class VideoController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "ping")
    @GetMapping("/ping")
    public ResponseResult<String> ping() throws Exception {
//        throw new CustomException(ResultCode.ERROR);
        return ResponseResult.okResult("ok");
    }

    @Operation(summary = "根据id获取视频")
    @GetMapping("/{id}")
    public ResponseResult<FileVo> getVideoById(@PathVariable(value = "id") Long id) {
        File file = fileService.getById(id);
        if (ObjectUtil.isNull(file)) {
            throw new CustomException(ResultCode.NOT_FOUND);
        } else {
            FileVo fileVo = BeanHelper.copyProperties(file, FileVo.class);
            return ResponseResult.okResult(fileVo);
        }

    }


}
