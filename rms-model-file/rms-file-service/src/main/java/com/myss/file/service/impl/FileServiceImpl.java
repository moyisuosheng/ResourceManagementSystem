package com.myss.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myss.file.domain.File;
import com.myss.file.service.FileService;
import com.myss.file.mapper.FileMapper;
import org.springframework.stereotype.Service;

/**
* @author moyis
* @description 针对表【file(文件信息)】的数据库操作Service实现
* @createDate 2023-12-06 16:54:20
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
    implements FileService {


}




