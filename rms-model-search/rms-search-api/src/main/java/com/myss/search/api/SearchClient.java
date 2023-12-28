package com.myss.search.api;

import com.myss.commons.model.ResponseResult;
import com.myss.commons.model.vo.flie.FileVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author moyis
 * @title: SearchClient
 * @projectName ResourceManagementSystem
 * @description: 搜索服务的远程调用api
 * @date 2023/12/1122:04
 */
@FeignClient(value = "rms-search-service", path = "/rms-search/v1/public/search")
public interface SearchClient {
    @PostMapping("/add")
    public ResponseResult add(@RequestBody FileVo fileVo) throws Exception;
}
