package com.myss.search.service;

import com.myss.search.domain.FileDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件存储库
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */

@Repository
public interface FileRepository extends ElasticsearchRepository<FileDto, Long> {

    /**
     * 按文件名查找文件 DTO
     *
     * @param fileName 文件名
     * @return {@link List}<{@link FileDto}>
     */
    List<FileDto> findFileDtoByFileName(String fileName);


}
