package com.myss.search;


import com.alibaba.fastjson2.JSON;
import com.myss.search.domain.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moyis
 * @title: MyIndexTest
 * @projectName ResourceManagementSystem
 * @description: TODO
 * @date 2023/12/1115:18
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyIndexTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testMatch() throws IOException {


        //构建搜索条件
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("fileName", "漫画"))
//                .withSorts(SortBuilders.fieldSort("type").order(SortOrder.DESC))
//                .withSorts(SortBuilders.fieldSort("score").order(SortOrder.DESC))
//                .withSorts(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
//                .withHighlightFields(
//                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
//                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
//                )
                .build();
        SearchHits<FileDto> search = elasticsearchRestTemplate.search(build, FileDto.class);
        SearchPage<FileDto> searchHits = SearchHitSupport.searchPageFor(search, build.getPageable());


        for (SearchHit<FileDto> discussPostSearchHit : searchHits) {
            //高亮
            log.info(discussPostSearchHit.getHighlightFields().toString());
            log.info(discussPostSearchHit.getContent().toString());
        }

        List<FileDto> files = handleResponse(searchHits);
        log.info(JSON.toJSONString(files));
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
