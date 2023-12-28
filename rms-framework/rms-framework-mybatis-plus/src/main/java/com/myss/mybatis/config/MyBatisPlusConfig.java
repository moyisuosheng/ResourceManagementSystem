package com.myss.mybatis.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.myss.commons.utils.AuthInfoHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MyBatisPlusConfig {

    // 不需要多租户的表
    private static final String[] tables = new String[]{"file", "tag"};

    //注册mybatis的插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页的插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 多租户插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {

            // 获取租户（企业）id
            @Override
            public Expression getTenantId() {
                // 从线程内取出企业id
                Long companyId = AuthInfoHolder.getCompanyId();
                if (ObjectUtil.isNotNull(companyId)) {
                    return new LongValue(companyId);
                } else {
                    return null;
                }
            }

            // 指定多租户字段
            @Override
            public String getTenantIdColumn() {
                return "company_id";
            }

            // true 表示不添加多租户，false 表示 添加多租户
            @Override
            public boolean ignoreTable(String tableName) {
                // 排除一下几张表使用多租户
                if (ArrayUtil.contains(tables, tableName)) {
                    return true;
                }
                // 企业id为null，不是用多租户
                return ObjectUtil.isNull(getTenantId());
            }
        }));
        return interceptor;
    }

}
