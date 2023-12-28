package com.myss.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.myss.commons.model.vo.AuthInfo;
import com.myss.commons.utils.AuthInfoHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        AuthInfo authInfo = AuthInfoHolder.getAuthInfo();

        long currentTimeMillis = System.currentTimeMillis();

        this.strictInsertFill(metaObject, "createTime", Long.class, currentTimeMillis);
        this.strictInsertFill(metaObject, "createUser", String.class, authInfo.getNickname());
        this.strictInsertFill(metaObject, "updateTime", Long.class, currentTimeMillis);
        this.strictInsertFill(metaObject, "updateUser", String.class, authInfo.getNickname());
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        AuthInfo authInfo = AuthInfoHolder.getAuthInfo();

        long currentTimeMillis = System.currentTimeMillis();

        this.strictUpdateFill(metaObject, "updateTime", Long.class, currentTimeMillis); // 起始版本 3.3.0(推荐)
        this.strictUpdateFill(metaObject, "updateUser", String.class, authInfo.getNickname()); // 起始版本
    }
}