package com.myss.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myss.file.domain.Tag;
import com.myss.file.service.TagService;
import com.myss.file.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author moyis
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2023-12-06 16:54:20
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {

}




