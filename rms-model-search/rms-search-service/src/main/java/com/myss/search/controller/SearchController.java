package com.myss.search.controller;

import com.google.common.collect.Lists;
import com.myss.commons.model.dto.PageParam;
import com.myss.commons.model.PageResponseResult;
import com.myss.commons.model.ResponseResult;
import com.myss.commons.model.vo.flie.FileVo;
import com.myss.commons.utils.BeanHelper;
import com.myss.file.api.FileClient;
import com.myss.search.domain.FileDto;
import com.myss.search.service.FileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索控制器
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */

@Tag(name = "搜索控制器")
@Slf4j
@RestController
@RequestMapping("/v1/public/search/")
public class SearchController {

    @Autowired
    private FileClient fileClient;

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Operation(summary = "ping")
    @GetMapping("/ping")
    public ResponseResult ping() {
        return ResponseResult.okResult("ping");
    }

    /**
     * 搜索所有
     *
     * @return {@link ResponseResult}
     * @throws IOException ioexception
     */
    @Operation(summary = "搜索所有")
    @GetMapping("/all")
    public ResponseResult<List<FileDto>> searchAll() throws IOException {
        return ResponseResult.okResult(
                Lists.newArrayList(fileRepository.findAll())
        );
    }

    /**
     * 根据输入文本搜索
     *
     * @return {@link ResponseResult}
     * @throws IOException ioexception
     */
    @Operation(summary = "根据输入文本搜索")
    @GetMapping("/text/{text}")
    public ResponseResult searchByText(@PathVariable("text") String text) throws IOException {
        ArrayList<FileDto> fileDtos = Lists.newArrayList(fileRepository.findFileDtoByFileName(text));
        return ResponseResult.okResult(fileDtos);
    }

    /**
     * 根据输入文本分页搜索
     *
     * @return {@link ResponseResult}
     * @throws IOException ioexception
     */
    @Operation(summary = "根据输入文本分页搜索")
    @GetMapping("/text/page/{text}")
    public PageResponseResult<List<FileDto>> searchByTextAndPage(@PathVariable("text") String text, @NonNull @RequestBody PageParam pageRequest) throws IOException {

        //构建搜索条件
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("fileName", text))
                .withPageable(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize()))
                .build();
        SearchHits<FileDto> search = elasticsearchRestTemplate.search(build, FileDto.class);
        SearchPage<FileDto> searchHits = SearchHitSupport.searchPageFor(search, build.getPageable());

        for (SearchHit<FileDto> discussPostSearchHit : searchHits) {
            //高亮
            log.info(discussPostSearchHit.getHighlightFields().toString());
            log.info(discussPostSearchHit.getContent().toString());
        }

        return new PageResponseResult(pageRequest, handleResponse(searchHits));
    }

    /**
     * 新增文档
     *
     * @return {@link ResponseResult}
     * @throws IOException ioexception
     */
    @Operation(summary = "新增文档")
    @PostMapping("/add")
    public ResponseResult add(@RequestBody FileVo fileVo) throws Exception {

        FileDto fileDto = BeanHelper.copyProperties(fileVo, FileDto.class);
        return ResponseResult.okResult(
                fileRepository.save(fileDto)
        );
    }


    private <T> List<T> handleResponse(SearchPage<T> searchHits) {
        ArrayList<T> list = new ArrayList<>();
        for (SearchHit<T> searchHit : searchHits) {
            T content = searchHit.getContent();
            list.add(content);
        }
        return list;
    }
}
