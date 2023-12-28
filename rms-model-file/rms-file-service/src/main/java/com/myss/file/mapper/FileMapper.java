package com.myss.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myss.file.domain.File;

import java.util.List;
import java.util.Map;

/**
 * @author moyis
 * @description 针对表【file(文件信息)】的数据库操作Mapper
 * @createDate 2023-12-06 16:54:20
 * @Entity com.myss.system.domain.FileVo
 */
public interface FileMapper extends BaseMapper<File> {

//    @MapKey("file_identifier")
    Map verifyFiles(List<String> hashList);

    String ping();

}




