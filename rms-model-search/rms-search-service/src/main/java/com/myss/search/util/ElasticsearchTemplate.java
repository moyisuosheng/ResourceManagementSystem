//package com.myss.search.util;
//
//import com.alibaba.fastjson.JSON;
//import com.myss.commons.model.dto.PageParam;
//import com.myss.commons.model.vo.video.FileVo;
//import com.myss.search.config.ElasticsearchConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Elasticsearch模板
// *
// * @author zhurongxu
// * @version 1.0.0
// * @date 2023/04/26
// */
//@Component
//@Slf4j
//public class ElasticsearchTemplate<T> {
//
//    // 声明MIME类型常量
//
//
//    /**
//     * Elasticsearch 配置
//     */
//    @Autowired
//    private ElasticsearchConfig elasticsearchConfig;
//
//
//    /**
//     * 客户端
//     */
//    private RestHighLevelClient client;
//
//    /**
//     * 初始化
//     *
//     * @throws Exception 例外
//     */
//    @PostConstruct
//    public void init() throws Exception {
//        client = new RestHighLevelClient(RestClient.builder(new HttpHost(elasticsearchConfig.getHost(), elasticsearchConfig.getPort())));
//    }
//
//    /**
//     * 结束时，释放链接
//     *
//     * @throws IOException ioexception
//     */
//    @PreDestroy
//    public void destroy() throws IOException {
//        if (client != null) {
//            client.close();
//        }
//    }
//
//
//    public Boolean addDocument(FileVo fileVo) throws Exception {
//        String json = JSON.toJSONString(fileVo);
//        IndexRequest indexRequest = new IndexRequest(elasticsearchConfig.getIndex()).id(fileVo.getId().toString());
//        indexRequest.source(json, XContentType.JSON);
//        client.index((indexRequest), RequestOptions.DEFAULT);
//        return true;
//    }
//
//    /**
//     * 判断索引库是否存在
//     *
//     * @throws IOException ioexception
//     */
//    public boolean indexExists() throws IOException {
//        // 1.创建Request对象
//        GetIndexRequest request = new GetIndexRequest(elasticsearchConfig.getIndex());
//        // 2.发送请求
//        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
//        // 3.输出
//        log.info(exists ? "索引库已经存在！" : "索引库不存在！");
//        return exists;
//    }
//
//    /**
//     * 查询所有
//     *
//     * @param t t
//     * @return {@link List}<{@link T}>
//     * @throws IOException ioexception
//     */
//    public List<T> matchAll(Class<T> t) throws IOException {
//        // 1.准备Request
//        SearchRequest request = new SearchRequest(elasticsearchConfig.getIndex());
//        // 2.准备DSL
//        request.source().query(QueryBuilders.matchAllQuery());
//        // 3.发送请求
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//        // 4.解析响应
//        return handleResponse(response, t);
//    }
//
//    /**
//     * 匹配
//     *
//     * @param fieldName 字段名称
//     * @param text      发短信
//     * @param t         t
//     * @return {@link List}<{@link T}>
//     * @throws IOException ioexception
//     */
//    public List<T> match(String fieldName, String text, Class<T> t) throws IOException {
//        // 1.准备Request
//        SearchRequest request = new SearchRequest(elasticsearchConfig.getIndex());
//        // 2.准备DSL
//        request.source()
//                .query(QueryBuilders.matchQuery(fieldName, text));
//        // 3.发送请求
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//        // 4.解析响应
//        return handleResponse(response, t);
//    }
//
//    /**
//     * 分页匹配
//     *
//     * @param fieldName   字段名称
//     * @param text        发短信
//     * @param t           t
//     * @param pageRequest 页面请求
//     * @return {@link List}<{@link T}>
//     * @throws IOException ioexception
//     */
//    public List<T> matchPage(String fieldName, String text, Class<T> t, PageParam pageRequest) throws IOException {
//        // 1.准备Request
//        SearchRequest request = new SearchRequest(elasticsearchConfig.getIndex());
//        // 2.准备DSL
//        request.source()
//                .query(QueryBuilders.matchQuery(fieldName, text))
//                .from(pageRequest.getPageNumber())
//                .pageSize(pageRequest.getPageSize());
//        // 3.发送请求
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//        // 4.解析响应
//        return handleResponse(response, t);
//    }
//
//    private List<T> handleResponse(SearchResponse response, Class<T> t) {
//        // 4.解析响应
//        SearchHits searchHits = response.getHits();
//        // 4.1.获取总条数
//        long total = searchHits.getTotalHits().value;
//        log.info("共搜索到" + total + "条数据");
//        // 4.2.文档数组
//        SearchHit[] hits = searchHits.getHits();
//        List<T> list = new ArrayList<>();
//        // 4.3.遍历
//        for (SearchHit hit : hits) {
//            // 获取文档source
//            String json = hit.getSourceAsString();
//            // 反序列化
//            T item = JSON.parseObject(json, t);
//            list.add(item);
//            log.info("fileVo = {}", item.toString());
//        }
//        return list;
//    }
//
//
//}