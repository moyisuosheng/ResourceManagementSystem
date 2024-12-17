package com.myss.search.controller;

import com.google.common.collect.Lists;
import com.myss.commons.model.PageResponseResult;
import com.myss.commons.model.ResponseResult;
import com.myss.commons.model.dto.PageParam;
import com.myss.commons.model.vo.flie.FileVo;
import com.myss.commons.utils.BeanHelper;
import com.myss.search.domain.FileDto;
import com.myss.search.repository.FileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//@RequiredArgsConstructor
public class SearchController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Operation(summary = "ping")
    @GetMapping("/ping")
    public ResponseResult<String> ping() {
        return ResponseResult.okResult("ping");
    }

    /**
     * 搜索所有
     *
     * @return {@link ResponseResult}
     */
    @Operation(summary = "搜索所有")
    @GetMapping("/all")
    public ResponseResult<List<FileDto>> searchAll() {
        return ResponseResult.okResult(
                Lists.newArrayList(fileRepository.findAll())
        );
    }

    /**
     * 根据输入文本搜索
     *
     * @return {@link ResponseResult}
     */
    @Operation(summary = "根据输入文本搜索")
    @GetMapping("/text/{text}")
    public ResponseResult<List<FileDto>> searchByText(@PathVariable("text") String text) {
        ArrayList<FileDto> list = Lists.newArrayList(fileRepository.findFileDtoByFileName(text));
        return ResponseResult.okResult(list);
    }

    /**
     * 根据输入文本分页搜索
     *
     * @return {@link ResponseResult}
     */
    @Operation(summary = "根据输入文本分页搜索")
    @GetMapping("/text/page/{text}")
    public PageResponseResult<List<FileDto>> searchByTextAndPage(@PathVariable("text") String text, @NonNull @RequestBody PageParam pageRequest) {

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
        return new PageResponseResult<>(pageRequest, handleResponse(searchHits));
    }

    /**
     * 新增文档
     *
     * @return {@link ResponseResult}
     */
    @Operation(summary = "新增文档")
    @PostMapping("/add")
    public ResponseResult<FileDto> add(@RequestBody FileVo fileVo) {

        FileDto fileDto = BeanHelper.copyProperties(fileVo, FileDto.class);
        FileDto save = fileRepository.save(fileDto);
        return ResponseResult.okResult(
                save
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
