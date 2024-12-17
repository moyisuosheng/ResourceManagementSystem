# 资源管理系统

# [ResourceManagementSystem]()

# 一、配置文件

## Gateway

```yaml
remote:
  ip: 172.18.68.8
spring:
  redis:
    host: ${remote.ip}
    port: 6379
    password: password
  cloud:
    sentinel:
      transport:
        dashboard: ${remote.ip}:8858
    gateway:
      globalcors:
        cors-configurations:
          '[/**]': # 匹配所有请求
            allowedOrigins: "*" #跨域处理 允许所有的域
            allowedMethods: # 支持的方法
              - GET
              - POST
              - PUT
              - DELETE
      routes:
        - id: content
          uri: lb://rms-video-service
          predicates:
            - Path=/rms-video/**
          # 地址前缀
          # filters:
          #   - StripPrefix=1
        - id: system
          uri: lb://rms-system-service
          predicates:
            - Path=/rms-system/**
          # 地址前缀
          # filters:
          #   - StripPrefix=1
        - id: search
          uri: lb://rms-search-service
          predicates:
            - Path=/rms-search/**
          # 地址前缀
          # filters:
          #   - StripPrefix=1
# logging:
#   level:
#     # com.alibaba.nacos.client.*: WARN
#     com.alibaba.nacos.client.*: INFO

ribbon:
  ConnectTimeout: 60000 # 连接超时时间(ms)
  ReadTimeout: 60000 # 通信超时时间(ms)

hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMillisecond: 60000 # 熔断超时时长：60000ms

logging:
  config: classpath:log4j2-dev.xml
```

## System

```
remote:
  ip: 172.18.68.8
server:
  servlet:
    #（可选项）配置项目路径，建议与应用项目名称保持一致
    context-path: /rms-system
    encoding:
      charset: UTF-8
spring:
  # main:
  #   allow-bean-definition-overriding: true  # 因为将来会引入很多依赖, 难免有重名的 bean
  aop:
    auto: true
  jackson:
    dateFormat: "yyyy-MM-dd HH:mm:ss"
    timeZone: GMT+8
    messageDateFormat: "yyyy-MM-dd HH:mm:ss"
    messageTimeZone: GMT+8
  servlet:
    multipart:
      enabled: true
      max-file-size: 5000MB
      max-request-size: 5000MB
  redis:
    host: ${remote.ip}
    port: 6379
    timeout: 5000
    password: password
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${remote.ip}:3306/rms-system?userUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: root
    type: com.alibaba.druid.pool.DruidDataSource
  cloud:
    sentinel:
      transport:
        dashboard: ${remote.ip}:8858
mybatis-plus:
  mapperLocations: classpath*:mapper/*.xml,classpath*:mapper/**/*.xml
  #别名包设置
  typeAliasesPackage: com.myss.system.mapper.**
  globalConfig:
    dbConfig:
      idType: ASSIGN_ID
      # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logicDeleteField: deleted
      # 逻辑已删除值(默认为 1)
      logicDeleteValue: 1
      # 逻辑未删除值(默认为 0)
      logicNotDeleteValue: 0
  #mybatis-plus配置控制台打印完整带参数SQL语句
  configuration:
    #输出sql日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true
    call-setters-on-nulls: true

swagger:
  enabled: true

minio:
  accessKey: minioadmin
  secretKey: minioadmin
  bucket: rms
  endpoint:  http://${remote.ip}:9001
  domain:  http://${remote.ip}:9001
#minio:
#  accessKey: awake-dev
#  secretKey: bPfm2ANQBC7xI81
#  bucket: awake-private-dev
#  endpoint:  https://dev-io.crec.cn
#  domain:  https://dev-io.crec.cn


xxl:
  job:
    accessToken: default_token
    admin:
      addresses: http://${remote.ip}:8080/xxl-job-admin
    executor:
      address: ''
      appName: lowcode-notice
      ip: ''
      logPath: log/notice
      logRetentionDays: 30
      port: 9998
      
seata:
  enable-auto-data-source-proxy: true
  enabled: true
  registry: # TC服务注册中心的配置，微服务根据这些信息去注册中心获取tc服务地址
    # 参考tc服务自己的registry.conf中的配置
    type: nacos
    nacos: # tc
      server-addr: ${remote.ip}:8848
      namespace: a942ac61-74e6-4207-a496-8ae7120ca7b6
      group: SEATA_GROUP
      # tc服务在nacos中的服务名称
      application: seata-server
      cluster: default
      # 事务组，根据这个获取tc服务的cluster名称
      username: nacos
      password: nacos
  tx-service-group: rms-server
  service:
    vgroup-mapping:
      rms-server: default


logging:
  config: classpath:log4j2-dev.xml

# ======== SpringDoc配置 ========
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    # 持久化认证数据，如果设置为 true，它会保留授权数据并且不会在浏览器关闭/刷新时丢失
    persistAuthorization: true
    path: doc.html

# ======== SpringDocs文档配置 ========
spring-docs:
  title: 系统服务 API Docs
  description: 系统服务 + OpenAPI Docs
  version: 0.0.1
  scheme: Bearer
  header: Authorization
```

## File

```
remote:
  ip: 172.18.68.8
server:
  port: 8030
  servlet:
    #（可选项）配置项目路径，建议与应用项目名称保持一致
    context-path: /rms-file
    encoding:
      charset: UTF-8
spring:
  # main:
  #   allow-bean-definition-overriding: true  # 因为将来会引入很多依赖, 难免有重名的 bean
  aop:
    auto: true
  jackson:
    dateFormat: "yyyy-MM-dd HH:mm:ss"
    timeZone: GMT+8
    messageDateFormat: "yyyy-MM-dd HH:mm:ss"
    messageTimeZone: GMT+8
  servlet:
    multipart:
      enabled: true
      max-file-size: 5000MB
      max-request-size: 5000MB
  redis:
    host: ${remote.ip}
    port: 6379
    timeout: 5000
    password: password
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${remote.ip}:3306/rms-file?userUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: root
    type: com.alibaba.druid.pool.DruidDataSource
  kafka:
    bootstrap-servers: ${remote.ip}:9192
    producer:
      retries: 10
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer


mybatis-plus:
  mapperLocations: classpath*:mapper/*.xml,classpath*:mapper/**/*.xml

  #别名包设置
  typeAliasesPackage: com.myss.file.mapper.**
  globalConfig:
    dbConfig:
      idType: ASSIGN_ID
      # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logicDeleteField: deleted
      # 逻辑已删除值(默认为 1)
      logicDeleteValue: 1
      # 逻辑未删除值(默认为 0)
      logicNotDeleteValue: 0
  configuration:
    #输出sql日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true
    call-setters-on-nulls: true

seata:
  enable-auto-data-source-proxy: true
  enabled: true
  registry: # TC服务注册中心的配置，微服务根据这些信息去注册中心获取tc服务地址
    # 参考tc服务自己的registry.conf中的配置
    type: nacos
    nacos: # tc
      server-addr: ${remote.ip}:8848
      namespace: a942ac61-74e6-4207-a496-8ae7120ca7b6
      group: SEATA_GROUP
      # tc服务在nacos中的服务名称
      application: seata-server
      cluster: default
      # 事务组，根据这个获取tc服务的cluster名称
      username: nacos
      password: nacos
  tx-service-group: rms-server
  service:
    vgroup-mapping:
      rms-server: default
swagger:
  enabled: true

minio:
  accessKey: minioadmin
  secretKey: minioadmin
  bucket: rms
  endpoint:  http://${remote.ip}:9001
  domain:  http://${remote.ip}:9001
#minio:
#  accessKey: awake-dev
#  secretKey: bPfm2ANQBC7xI81
#  bucket: awake-private-dev
#  endpoint:  https://dev-io.crec.cn
#  domain:  https://dev-io.crec.cn


xxl:
  job:
    accessToken: default_token
    admin:
      addresses: http://${remote.ip}:8080/xxl-job-admin
    executor:
      address: ''
      appName: lowcode-notice
      ip: ''
      logPath: log/notice
      logRetentionDays: 30
      port: 9998

logging:
  config: classpath:log4j2-dev.xml

# ======== SpringDoc配置 ========
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    # 持久化认证数据，如果设置为 true，它会保留授权数据并且不会在浏览器关闭/刷新时丢失
    persistAuthorization: true
    path: doc.html

# ======== SpringDocs文档配置 ========
spring-docs:
  title: 文件服务 API Docs
  description: 文件服务 + OpenAPI Docs
  version: 0.0.1
  scheme: Bearer
  header: Authorization
```

## Search

```
remote:
  ip: 172.18.68.8
server:
  servlet:
    #（可选项）配置项目路径，建议与应用项目名称保持一致
    context-path: /rms-file
    encoding:
      charset: UTF-8
spring:
  # main:
  #   allow-bean-definition-overriding: true  # 因为将来会引入很多依赖, 难免有重名的 bean
  aop:
    auto: true
  jackson:
    dateFormat: "yyyy-MM-dd HH:mm:ss"
    timeZone: GMT+8
    messageDateFormat: "yyyy-MM-dd HH:mm:ss"
    messageTimeZone: GMT+8
  servlet:
    multipart:
      enabled: true
      max-file-size: 5000MB
      max-request-size: 5000MB
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${remote.ip}:3306/rms-search?userUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: root
    type: com.alibaba.druid.pool.DruidDataSource
  redis:
    host: ${remote.ip}
    port: 6379
    timeout: 5000
    password: password
  sentinel:
    transport:
      dashboard: ${remote.ip}:8858
      client-ip: 192.168.249.1
  elasticsearch:
    uris: ${remote.ip}:9200
  kafka:
    bootstrap-servers: ${remote.ip}:9192
    listener:
      # 手工ack，调用ack后立刻提交offset
      ack-mode: manual_immediate
      # 消费者监听容器的并发数量(线程数)
      concurrency: 2
    producer:
      # 消息重发次数
      retries: 0
      # 一个批次可以使用的内存大小
      batch-size: 16384
      # 设置生产者内存缓冲区大小
      buffer-memory: 33554432
      # 键的序列化方式
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      # 值的序列化方式
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    consumer:
      # 自动提交的间隔
      auto-commit-interval: 1S
      # 该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：从头开始读取
      auto-offset-reset: earliest
      # 是否自动提交偏移量，默认值是true ，为了避免重复数据和数据丢失，可以把它设置为false，然后手动提交偏移量
      enable-auto-commit: false
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer

mybatis-plus:
  mapperLocations: classpath*:mapper/*.xml,classpath*:mapper/**/*.xml

  #别名包设置
  typeAliasesPackage: com.myss.file.mapper.**
  globalConfig:
    dbConfig:
      idType: ASSIGN_ID
      # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logicDeleteField: deleted
      # 逻辑已删除值(默认为 1)
      logicDeleteValue: 1
      # 逻辑未删除值(默认为 0)
      logicNotDeleteValue: 0
  configuration:
    #输出sql日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true
    call-setters-on-nulls: true

swagger:
  enabled: true

minio:
  accessKey: minioadmin
  secretKey: minioadmin
  bucket: rms
  endpoint: 127.0.0.1:9001
  domain: 127.0.0.1:9001
#minio:
#  accessKey: awake-dev
#  secretKey: bPfm2ANQBC7xI81
#  bucket: awake-private-dev
#  endpoint:  https://dev-io.crec.cn
#  domain:  https://dev-io.crec.cn


xxl:
  job:
    accessToken: default_token
    admin:
      addresses: http://127.0.0.1:8080/xxl-job-admin
    executor:
      address: ''
      appName: lowcode-notice
      ip: ''
      logPath: log/notice
      logRetentionDays: 30
      port: 9998

logging:
  config: classpath:log4j2-dev.xml

seata:
  enable-auto-data-source-proxy: true
  enabled: true
  registry: # TC服务注册中心的配置，微服务根据这些信息去注册中心获取tc服务地址
    # 参考tc服务自己的registry.conf中的配置
    type: nacos
    nacos: # tc
      server-addr: ${remote.ip}:8848
      namespace: a942ac61-74e6-4207-a496-8ae7120ca7b6
      group: SEATA_GROUP
      # tc服务在nacos中的服务名称
      application: seata-server
      cluster: default
      # 事务组，根据这个获取tc服务的cluster名称
      username: nacos
      password: nacos
  tx-service-group: rms-server
  service:
    vgroup-mapping:
      rms-server: default

# ======== SpringDoc配置 ========
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    # 持久化认证数据，如果设置为 true，它会保留授权数据并且不会在浏览器关闭/刷新时丢失
    persistAuthorization: true
    path: doc.html

# ======== SpringDocs文档配置 ========
spring-docs:
  title: 文件服务 API Docs
  description: 文件服务 + OpenAPI Docs
  version: 0.0.1
  scheme: Bearer
  header: Authorization
```

# 二、Docker容器部署脚本

## minio

创建文件夹

```sh
mkdir -p /opt/docker/minio/{data,config}
```

创建容器

```dockerfile
docker run -p 9000:9000 -p 9090:9090 \
     --name minio \
     -d --restart=always \
     -e "MINIO_ACCESS_KEY=minioadmin" \
     -e "MINIO_SECRET_KEY=minioadmin" \
     -v /opt/docker/minio/data:/data \
     -v /opt/docker/minio/config:/root/.minio \
     minio/minio server \
     /data --console-address ":9090" -address ":9000"
```

## nacos

### 创建文件夹

```
mkdir -p /opt/docker/nacos/{data,conf,logs}
```

### 初始容器

```
docker run -d --name nacos \
-p 8848:8848 \
-p 9848:9848 \
-p 9555:9555 \
-e PREFER_HOST_MODE=hostname \
-e MODE=standalone \
-d nacos/nacos-server
```

### 复制配置文件

```
docker cp nacos:/home/nacos/conf /opt/docker/nacos/
```

### 删除原有的容器

```
docker rm -f nacos
```

### 重新部署

```
docker run --name nacos \
--restart=always \
-e MODE=standalone \
-v /opt/docker/nacos/conf/application.properties:/home/nacos/conf/application.properties \
-v /opt/docker/nacos/logs/:/home/nacos/logs \
-v /opt/docker/nacos/data/:/home/nacos/data \
-e PREFER_HOST_MODE=hostname \
-p 8848:8848 \
-p 9848:9848 \
-d nacos/nacos-server
```

补充

```
-e PREFER_HOST_MODE=hostname \
--env NACOS_AUTH_ENABLE=true \
-e NACOS_AUTH_TOKEN=SecretKeyM1Z2WDc4dnVyZkQ3NmZMZjZ3RHRwZnJjNFROdkJOemEK \
-e NACOS_AUTH_IDENTITY_KEY=mpYGXyu7 \
-e NACOS_AUTH_IDENTITY_VALUE=mpYGXyu7 \
```

## Seata

版本：`1.8.0.2`

### 建表语句

```sql
--
-- Licensed to the Apache Software Foundation (ASF) under one or more
-- contributor license agreements.  See the NOTICE file distributed with
-- this work for additional information regarding copyright ownership.
-- The ASF licenses this file to You under the Apache License, Version 2.0
-- (the "License"); you may not use this file except in compliance with
-- the License.  You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_status_gmt_modified` (`status` , `gmt_modified`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(128),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `status`         TINYINT      NOT NULL DEFAULT '0' COMMENT '0:locked ,1:rollbacking',
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_status` (`status`),
    KEY `idx_branch_id` (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `distributed_lock`
(
    `lock_key`       CHAR(20) NOT NULL,
    `lock_value`     VARCHAR(20) NOT NULL,
    `expire`         BIGINT,
    primary key (`lock_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('AsyncCommitting', ' ', 0);
INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('RetryCommitting', ' ', 0);
INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('RetryRollbacking', ' ', 0);
INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('TxTimeoutCheck', ' ', 0);


CREATE TABLE IF NOT EXISTS `vgroup_table`
(
    `vGroup`    VARCHAR(255),
    `namespace` VARCHAR(255),
    `cluster`   VARCHAR(255),
  UNIQUE KEY `idx_vgroup_namespace_cluster` (`vGroup`,`namespace`,`cluster`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
```

nacos中配置

​	分组：`SEATA_GROUP`

​	名称：`seataServer.properties`

```
#Transaction storage configuration, only for the server.
store.mode=db
store.lock.mode=db
store.session.mode=db

#These configurations are required if the `store mode` is `db`.
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.cj.jdbc.Driver
store.db.url=jdbc:mysql://mysql:3306/seata?useUnicode=true&rewriteBatchedStatements=true
store.db.user=root
store.db.password=root
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.distributedLockTable=distributed_lock
store.db.vgroupTable=vgroup-table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
```

### 初始化容器

```
docker run --name seata \
-p 8091:8091 \
-p 7091:7091 \
-e SEATA_IP=127.0.0.1 \
-e SEATA_PORT=8091 \
seataio/seata-server
```

### 使用自定义配置文件

```
docker cp seata:/seata-server/resources /opt/docker/seata
```

### 删除初始容器

```
docker rm -f seata
```

### 创建网络

```
docker network create seata_net
```

### 将nacos、mysql加入网络

```
docker network connect seata_net nacos
docker network connect seata_net mysql
```

### 查看网络内部信息

```
docker network inspect seata_net
```

### 启动服务

#### 使用`Bridge`(有问题)

```
docker run --name seata \
--restart=always \
-p 8091:8091 \
-p 7091:7091 \
--net seata_net \
-e SEATA_IP=172.18.68.8 \
-e SEATA_PORT=8091 \
-v /opt/docker/seata/resources:/seata-server/resources  \
-d seataio/seata-server
```

 注释：-e -e SEATA_IP=172.20.130.126 的ip地址需要是宿主机的ip，不然会连不上seata

#### 使用本机(外网无法访问)

```
docker run --name seata \
--restart=always \
--net host \
-e SEATA_IP=172.18.68.8 \
-e SEATA_PORT=8091 \
-v /opt/docker/seata/resources:/seata-server/resources  \
-d seataio/seata-server
```

### 设置容器自启动

```
docker update --restart=always seata
```

### 取消容器自启动

```
docker update --restart=no seata
```

### 查看容器日志

```
docker logs -f seata
```

### 移除指定的网络

```
docker network rm default_network
```

## Sentinel 

默认账号和密码：sentinel

```
docker run -d --name sentinel -p 8858:8858 bladex/sentinel-dashboard
```



```
docker run -d --name sentinel -p 8858:8858 \
-e "-Dproject.name=sentinel -Dsentinel.dashboard.auth.username=bsfc -Dsentinel.dashboard.auth.password=bsfc" \
bladex/sentinel-dashboard
```



## mongoDB

### 创建文件夹

```
mkdir -p /opt/docker/mongodb/data
```

### 启动服务

```bash
docker run -d \
--name mongodb \
-p 27017:27017 \
-v /opt/docker/mongodb/data:/data/db \
mongo
```

## redis

```sh
docker run --restart=always \
-d \
-p 6379:6379 \
--name redis \
-v /opt/docker/redis/redis.conf:/etc/redis/redis.conf \
-v /opt/docker/redis/data:/data \
-d redis:latest redis-server /etc/redis/redis.conf --appendonly yes --requirepass password
```

参数解释：

> `-p 6379:6379`：把容器内的6379端口映射到`宿主机`6379端口
>
> `-v /data/redis/redis.conf:/etc/redis/redis.conf`：把宿主机配置好的redis.conf放到容器内的这个位置中
>
> `-v /data/redis/data:/data`：把redis持久化的数据在宿主机内显示，做数据备份
>
> `redis-server /etc/redis/redis.conf`：这个是关键配置，让redis不是无配置启动，而是按照这个redis.conf的配置启动
>
> `--appendonly yes`：redis启动后数据持久化
>
> `--requirepass password`：设置密码为 `password`

## mysql

```sh
docker run \
--name mysql \
-d \
-p 3306:3306 \
-v /opt/docker/mysql/log:/var/log/mysql \
-v /opt/docker/mysql/data:/var/lib/mysql \
-v /opt/docker/mysql/conf.d/my.cnf:/etc/mysql/conf.d/my.cnf \
-e MYSQL_ROOT_PASSWORD=root \
--restart=always \
--privileged=true \
mysql:latest
```

参数解释：

> `--name mysql`：设置容器名称
>
> `-v /opt/docker/mysql/log:/var/log/mysql`：把宿主机配置好的redis.conf放到容器内的这个位置中
>
> `-v /opt/docker/mysql/data:/var/lib/mysql`：把mysql持久化的数据在宿主机内显示，做数据备份
>
> `-v /opt/docker/mysql/conf.d/my.cnf:/etc/mysql/conf.d/my.cnf`：把宿主机配置好的my.cnf放到容器内的这个位置中，mysql8.0之后配置。
>
> `-e MYSQL_ROOT_PASSWORD=root`：设置mysql密码
>
> `--restart=always`：自动启动容器
>
> `--privileged=true`：容器内的root拥有`宿主机`真正的root权限，否则容器内的root权限只是`宿主机`的普通用户。
>
> `mysql:latest`：最新版本mysql

## pg

```
docker pull postgres:11.14

docker run --name pgsql -p 5432:5432 -e POSTGRES_PASSWORD=root -v pgdata:/var/lib/postgresql/data --restart=always -d postgres:11.14
```

## kafka

```Dockerfile
version: "2"

services:
  zookeeper:
    image: docker.io/bitnami/zookeeper:3.8
    restart: always
    ports:
      - "22181:2181"
    volumes:
      - "zookeeper_data:/bitnami"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    container_name: kafka1
    image: docker.io/bitnami/kafka:3.4
    restart: always
    ports:
      - "9192:9092"
    volumes:
      - "kafka_data:/bitnami"
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://172.26.138.194:9192
      - KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE=true
    depends_on:
      - zookeeper

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    restart: always
    ports:
        - 10010:8080
    volumes:
        - /etc/localtime:/etc/localtime
    environment:
        - KAFKA_CLUSTERS_0_NAME=local
        - KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka1:9092
        - AUTH_TYPE=LOGIN_FORM
        - SPRING_SECURITY_USER_NAME=admin
        - SPRING_SECURITY_USER_PASSWORD=admin
volumes:
  zookeeper_data:
    driver: local
  kafka_data:
    driver: local
    
```

解释

```
192.168.249.133为本机服务器地址
```

文件名

```
docker-compose.yml
```

执行命令：

```sh
docker compose up
```

## Logstash:elk







```
version: '3'
services:
  elk:
    image: sebp/elk
    container_name: elk
    restart: always
    ports:
      - "5601:5601"
      - "9200:9200"
      - "5044:5044"
    volumes:
      - /opt/docker/elk/elasticsearch/data:/var/lib/elasticsearch
      - /opt/docker/elk/logstash/conf.d:/etc/logstash/conf.d
      - /opt/docker/elk/logstash/config:/opt/logstash/config
      - /opt/docker/elk/kibana/kibana.yml:/opt/kibana/config/kibana.yml
    environment:
      - TZ=Asia/Shanghai
```

















### 1.创建文件夹

```
mkdir -p /opt/docker/elk/kibana/conf/
mkdir -p /opt/docker/elk/data
mkdir -p /opt/docker/elk/logstash/config
```



```sh
docker run -d --restart=always --name elk -p 5602:5601 -p 9201:9200 -p 5044:5044 dockerproxy.cn/sebp/elk:771
```



```
docker run -d --restart=always --name elk -p 5602:5601 -p 9201:9200 -p 5044:5044 sebp/elk:771
```

### 2、进入文件夹

```
cd /opt/docker/elk

docker cp -a elk:/opt/kibana/config/kibana.yml ./kibana

docker cp -a elk:/opt/logstash/config ./logstash/config
```

### 3、部署脚本

```
version: '3'
services:
  elk:
    image: sebp/elk
    container_name: elk
    restart: always
    ports:
      - "5602:5601"
      - "9201:9200"
      - "5044:5044"
    volumes:
      - /opt/docker/elk/elasticsearch/data:/var/lib/elasticsearch
      - /opt/docker/elk/logstash/conf.d:/etc/logstash/conf.d
      - /opt/docker/elk/logstash/config:/opt/logstash/config
      - /opt/docker/elk/kibana/kibana.yml:/opt/kibana/config/kibana.yml
    networks:
      - elk_net
    environment:
      - TZ=Asia/Shanghai
      
networks: 
  elk_net:
    external: true
```

2.命令

```
docker run -d --restart=always --name elk -p 5602:5601 -p 9201:9200 -p 5044:5044 \
 -v /opt/docker/elk/kibana/conf/kibana.yml:/opt/kibana/config/kibana.yml \
 -v /opt/docker/elk/data:/var/lib/elasticsearch \
 -v /opt/docker/elk/logstash/config:/opt/logstash/config \
 dockerproxy.cn/sebp/elk:771
```

3.命令解释

```
-p 5601:5601 映射kibana端口
-p 9200:9200 映射es端口
-p 5044:5044 映射logstash端口
-v /root/data/es/conf/kibana.yml:/opt/kibana/config/kibana.yml 挂载kibana配置文件
-v /root/data/es/data:/var/lib/elasticsearch 挂载es数据源
-v /root/data/logstash/config:/opt/logstash/config  挂载logstash配置
--restart=always  自动启动
```



#### 修改系统的vm.max_map_count

基于[elk-docker Prerequisites](https://elk-docker.readthedocs.io/#prerequisites)的说明，需要将系统的vm.max_map_count设置为262144或更多。

1. 修改文件/etc/sysctl.conf

```shell
sudo vi /etc/sysctl.conf
```

1. 增加以下属性

```shell
vm.max_map_count=262144
```

1. 查看修改结果

```shell
sysctl -p
```

### 安装和配置ELK服务

#### 获取sebp/elk的原始配置文件

```shell
// 在运行容器并且把容器里的配置cp到宿主机当中
docker run --name elk sebp/elk

docker cp -a elk:/opt/kibana/config/kibana.yml ./kibana

docker cp -a elk:/opt/logstash/config ./logstash/config

//然后删除刚才创建的容器
docker stop -f elk

docker rm -f elk
```

#### 编写docker-compose文件

```shell
version: '3'
services:
  elk:
    image: sebp/elk
    container_name: elk
    restart: always
    ports:
      - "5601:5601"
      - "9200:9200"
      - "5044:5044"
    volumes:
      - /opt/docker/elk/elasticsearch/data:/var/lib/elasticsearch
      - /opt/docker/elk/logstash/conf.d:/etc/logstash/conf.d
      - /opt/docker/elk/logstash/config:/opt/logstash/config
      - /opt/docker/elk/kibana/kibana.yml:/opt/kibana/config/kibana.yml
    networks:
      - elk_net
    environment:
      - TZ=Asia/Shanghai
      
networks: 
  elk_net:
    external: true
```

#### 单机版docker-compose文件

```shell
version: '3'
services:
  elk:
    image: sebp/elk
    container_name: elk
    restart: always
    ports:
      - "5601:5601"
      - "9200:9200"
      - "5044:5044"
    volumes:
      - /home/elk/elasticsearch/data:/var/lib/elasticsearch
      - /home/elk/logstash/conf.d:/etc/logstash/conf.d
      - /home/elk/logstash/config:/opt/logstash/config
      - /home/elk/kibana/kibana.yml:/opt/kibana/config/kibana.yml
    environment:
      - TZ=Asia/Shanghai
```

## 配置Logstash

#### 创建配置文件

```shell
vi ./logstash/conf.d/yourconfigname.conf
```

#### 根据需求配置文件内容

```shell
input {
  beats {
    port => 5044
  }
}
output {
  stdout {
    codec => rubydebug
  }
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "%{[@metadata][beat]}-%{[@metadata][version]}-%{+YYYY.MM.dd}"
  }
}
```

## 汉化kibana

#### 修改配置文件

```shell
vi ./kibana/kibana.yml
```

#### 在文件末尾增加如下内容

```shell
i18n.locale: "zh-CN"
```

#### 启动ELK

```shell
docker-compose -f ./docker-compose.yml up -d
```

启动完成后访问 [http://ip:5601](http://ip:5601/)

## es

创建网络

```
docker network create elastic
```

查看网络

```
docker network ls
```

拉取镜像

```dockerfile
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.15.2
```

部署es

```dockerfile
docker run -d \
--name es \
--restart=always \
--network elastic \
-v /opt/docker/es/data:/usr/share/elasticsearch/data \
-v /opt/docker/es/plugins:/usr/share/elasticsearch/plugins \
-p 9200:9200 \
-p 9300:9300 \
-e "discovery.type=single-node" \
docker.elastic.co/elasticsearch/elasticsearch:7.10.0
```

问题：

启动失败：
排查是否已经创建/opt/docker/es文件夹和文件夹权限

获取权限指令：

```sh
chmod 777 /opt/docker/es/**
```

例如：

```dockerfile
docker run -d \
	--name es \
    -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
    -e "discovery.type=single-node" \
    -v es-data:/usr/share/elasticsearch/data \
    -v es-plugins:/usr/share/elasticsearch/plugins \
    --privileged \
    --network es-net \
    -p 9200:9200 \
    -p 9300:9300 \
elasticsearch:7.12.1
```

上述 Docker 命令是为了运行 Elasticsearch 容器。下面是对命令的解释：

docker run -d： 这部分表示在后台运行容器。

--name es： 为容器指定一个名字，这里是 “es”。

-e "ES_JAVA_OPTS=-Xms512m -Xmx512m"： 设置 Java 虚拟机的参数，包括初始堆内存大小 (-Xms) 和最大堆内存大小 (-Xmx)，这里都设置为
512MB。

-e "discovery.type=single-node"： 设置 Elasticsearch 的节点发现机制为单节点，因为在这个配置中只有一个 Elasticsearch 实例。

-v es-data:/usr/share/elasticsearch/data： 将容器内 Elasticsearch 的数据目录挂载到宿主机的名为 “es-data” 的卷上，以便数据持久化。

-v es-plugins:/usr/share/elasticsearch/plugins： 类似上面，将容器内 Elasticsearch 的插件目录挂载到宿主机的名为
“es-plugins” 的卷上。

--privileged： 赋予容器一些特权，可能会有一些安全风险，需要慎用。

--network es-net： 将容器连接到名为 “es-net” 的网络上，目的是为了与其他容器进行通信。

-p 9200:9200 -p 9300:9300： 将容器内部的端口映射到宿主机上，这里分别是 Elasticsearch 的 HTTP REST API
端口（9200）和节点间通信的端口（9300）。

elasticsearch:7.12.1： 指定要运行的 Docker 镜像的名称和版本号，这里是 Elasticsearch 7.12.1 版本。

这个命令配置了 ElasticSearch 的运行参数、数据卷、网络等，使其能够在后台运行，并且可以通过指定的端口访问 Elasticsearch 的
API。

【官网链接】[使用 Docker 安装 Kibana |Kibana
指南 [8.15\] |弹性的 (elastic.co)](https://www.elastic.co/guide/en/kibana/current/docker.html)

## kabana

加入 es 网络

```dockerfile
docker run -d \
--restart=always \
--name kibana \
-e ELASTICSEARCH_HOSTS=http://es:9200 \
--network=elastic \
-p 5601:5601  \
docker.elastic.co/kibana/kibana:7.10.0
```

这 Docker 命令是为了运行 Kibana 容器，它与 Elasticsearch 一同组成了 ELK（Elasticsearch, Logstash,
Kibana）技术栈的一部分。下面是对命令各个部分的解释：

docker run -d： 在后台运行容器。

--name kibana： 为容器指定一个名字，这里是 “kibana”。

-e ELASTICSEARCH_HOSTS=http://es:9200： 设置 Kibana 运行时连接的 Elasticsearch 节点的地址，这里指定了 Elasticsearch
服务的地址为 http://es:9200，其中 “es” 是 Elasticsearch 服务的容器名，而不是具体的 IP 地址。这是因为在 --network=es-net
中指定了容器连接到 “es-net” 网络，容器名会被解析为相应的 IP 地址。

--network=elastic： 将容器连接到名为 “elastic” 的网络上，确保 Kibana 能够与 Elasticsearch 容器进行通信。

-p 5601:5601： 将容器内部的 5601 端口映射到宿主机上，允许通过宿主机的 5601 端口访问 Kibana 的 Web 界面。

docker.elastic.co/kibana/kibana:7.10.0： 指定要运行的 Docker 镜像的名称和版本号，这里是 kibana:7.10.0 版本。

## sentinel

```
docker run --name sentinel -d -p 8858:8858 bladex/sentinel-dashboard
```

# 三、Docker + idea docker插件实现远程部署项目

## 1.1 idea 安装 Docker 插件，进行简单配置

## 1.2 Docker 开启`支持远程访问`

### 1.2.1 查看默认配置参数

想要开启远程访问，就需要修改这个配置文件

```sh
/usr/lib/systemd/system/docker.service
```

首先查看默认配置参数并筛选关键信息“ExecStart”

```sh
cat /usr/lib/systemd/system/docker.service | grep ExecStart
```

预计响应结果：

```txt
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
```

#### 1.2.2 添加远程访问参数

需要追加的参数如下：

```sh
-H tcp://0.0.0.0:2375
```

或者直接使用下面的替换命令直接添加

```sh
sed -i 's#ExecStart=.*#ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock -H tcp://0.0.0.0:2375#' /usr/lib/systemd/system/docker.service
```

查看修改效果指令：

```sh
cat /usr/lib/systemd/system/docker.service | grep ExecStart
```

查看修改效果指令的预计执行结果：

```txt
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock -H tcp://0.0.0.0:2375 
```

修改配置之后，需要手动重新加载配置，命令如下

```sh
systemctl daemon-reload&&systemctl restart docker
```

#### 1.2.3 验证指令

```sh
ss -nltp
```

## 1.3 项目中新建 Dockerfile 文件

文件名：Dockerfile

文件模板如下：

```dockerfile
FROM java:8
VOLUME /tmp/次级目录
MAINTAINER myss
ADD /target/${打包后jar包名}  ${别名}
ENTRYPOINT ["java","-Dprot=端口","-jar","/别名"]
```

例如：

```dockerfile
FROM java:8
VOLUME /tmp/rms-system
MAINTAINER myss
ADD /target/rms-system-service-1.0-SNAPSHOT.jar  rms-system-server.jar
ENTRYPOINT ["java","-Dprot=8020","-jar","/rms-system-server.jar"]
```

## 1.4 idea的docker插件配置

### 1.4.1 连接远程docker服务

1.4.1.1、依次点击：`服务`  -> `+`  -> `Docker`  -> `Docker 连接`，打开Docker配置界面；

1.4.1.2、命名docker连接后，选择`TCP套接字`模式，在`引擎API URL`中输入：`http://remoteIp:2375`，`证书文件夹`
可不选，便可连接远程docker服务。

# 四、Logstash

## 2.1 安装Logstash

下载整合了ES Kibana LogStatsh的镜像

```
docker pull sebp/elk:771 
```

创建容器并启动

```
docker run -d --restart=always  --name  elk -p 5602:5601 -p 9201:9200 -p 5044:5044  sebp/elk:771
```

端口说明

```
5602 - Kibana web 接口
9201 - Elasticsearch JSON 接口
5044 - Logstash 日志接收接口
```

查看启动日志

```shell
docker logs -f elk
```

可能出现的错误

```
max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]

切换到root用户修改配置sysctl.conf
vi /etc/sysctl.conf 

添加下面配置：
vm.max_map_count=655360

并执行命令：
sysctl -p

然后，重新启动elk，即可启动成功。
docker start elk
```

浏览器输入：http://remoteIp:9201/ ，启动成功预期响应：

```
{
  "name" : "elk",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "8NtqvXa0SnOGlzcwpFh70Q",
  "version" : {
    "number" : "7.7.1",
    "build_flavor" : "default",
    "build_type" : "tar",
    "build_hash" : "ad56dce891c901a492bb1ee393f12dfff473a423",
    "build_date" : "2020-05-28T16:30:01.040088Z",
    "build_snapshot" : false,
    "lucene_version" : "8.5.1",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```

稍等几十秒，浏览器输入：http://remoteIp:5620/ ，进入kibana则启动成功。

## 2.2 配置LogStatsh

启动成功后 我们需要做下简单的配置，首先是LogStatsh的配置.

进入到容器中

```sh
docker exec -it -u root elk bash
```

编辑logStatsh的配置

```sh
vim /etc/logstash/conf.d/02-beats-input.conf
```

覆盖配置

```
input {
    tcp {
        port => 5044
        mode => "server"
        type => json
    }
} 
output{
    elasticsearch {
        hosts => ["localhost:9200"]
        action => "index"
        codec => rubydebug
        index => "log4j2-%{+YYYY.MM.dd}"
    }
}
```

配置解析：

input为输入的配置，output为输出的配置，此配置文件端口对应docker容器端口，非宿主机端口。

```
input.tcp.port：数据输入端口
input.tcp.mode：
input.tcp.type：将数据json化存储
```

output.elasticsearch.hosts

```
es地址
例：
	hosts => ["10.0.xx.xx:9200", "10.0.xx.xx:9200", "10.0.xx.xx:9200"]
```

output.elasticsearch.action

```
index：新增覆盖
delete：通过id删除文档需要指定 document_id
update：修改/修改或新增，upsert
create：新增不覆盖
```

output.elasticsearch.codec

```
Logstash编码格式
	Plain：输入什么，输出就是什么
	rubydebug：方便调试的
		 查看debug日志指令：bin/logstash -f test_grok.conf --verbose --debug
		 这样直接在前台启动,便可以看到日志输出到终端.
```

output.elasticsearch.index

```
写入事件所用的索引。可以动态的使用%{foo}语法，它的默认值是：
"logstash-%{+YYYY.MM.dd}"，以天为单位分割的索引，使你可以很容易的删除老的数据或者搜索指定时间范围内的数据。
索引不能包含大写字母。推荐使用以周为索引的ISO 8601格式，例如logstash-%{+xxxx.ww}

示例：
	index => "%{[fields][product_type]}-transaction-%{+YYYY-MM-dd}"
```

修改前 /etc/logstash/conf.d/02-beats-input.conf 文件内容

```conf
input {
  beats {
    port => 5044
    ssl => true
    ssl_certificate => "/etc/pki/tls/certs/logstash-beats.crt"
    ssl_key => "/etc/pki/tls/private/logstash-beats.key"
  }
}
```

修改完毕后输入`exit`指令退出容器，然后重启容器

```
docker restart elk
```

## 2.3 配置项目

项目中添加依赖（如果使用log4j2 不需要到此坐标）

```xml

<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>5.2</version>
</dependency>
```

添加日志配置文件log4j2-dev.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="OFF" monitorInterval="60">
    <!--先定义所有的appender-->
    <appenders>
        <!--这个输出控制台的配置-->
        <console name="Console" target="SYSTEM_OUT">
            <!--输出日志的格式-->
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
        </console>
        <Socket name="Socket" host="192.168.249.133" port="5044" protocol="TCP">
            <PatternLayout pattern="%d{yyyy.MM.dd 'at' HH:mm:ss z} %-5level %class{36} %M() @%L - %msg%n"/>
        </Socket>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <root level="INFO">
            <appender-ref ref="Console"/>
            <appender-ref ref="Socket"/>
        </root>
    </loggers>
</configuration>
```

## 2.4 配置索引

2.4.1 左侧 `菜单栏` -> `Management`

2.4.2 查找 `Index Patterns`

2.4.3 在搜索栏输入 `*` ,查询所有，再关闭右侧弹出窗，点击`Create index pattern`按钮。

2.4.4 在 `Index pattern` 输入框输入 `log*`，这里和 `log4j2-dev.xml` 文件 -> `Socket`标签 -> `PatternLayout` -> 子标签 ->
`pattern`属性相关 `可改`。

2.4.5 再点击 左侧 `菜单栏` -> `Discover` 即可看到日志数据。

# 五、SpringDoc

1、 [bootstrap.yml](rms-model-system\rms-system-service\src\main\resources\bootstrap.yml) 添加

```yaml
# ======== SpringDoc配置 ========
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    # 持久化认证数据，如果设置为 true，它会保留授权数据并且不会在浏览器关闭/刷新时丢失
    persistAuthorization: true
    path: doc.html

# ======== SpringDocs文档配置 ========
spring-docs:
  title: 文件服务 API Docs
  description: 文件服务 + OpenAPI Docs
  version: 0.0.1
  scheme: Bearer
  header: Authorization
```

2、 [rms-framework-web](rms-framework\rms-framework-web) 公共模块添加 `配置类`，`gateway无需引入本模块`。

```java
package com.myss.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Spring Docs 属性
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Component
@ConfigurationProperties(prefix = "spring-docs")
@Setter
@Getter
public class SpringDocsProperties {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 版本
     */
    private String version;
    /**
     * 页眉
     */
    private String header;
    /**
     * 方案
     */
    private String scheme;
}

```

```java
package com.myss.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc配置文件
 */
@Configuration
public class SpringDocConfig {

    @Autowired
    private SpringDocsProperties springDocsProperties;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                // 文档描述信息
                .info(new Info()
                        .title(springDocsProperties.getTitle())
                        .description(springDocsProperties.getDescription())
                        .version(springDocsProperties.getVersion())
                )
                // 添加全局的header参数
                .addSecurityItem(new SecurityRequirement()
                        .addList(springDocsProperties.getHeader()))
                .components(new Components()
                        .addSecuritySchemes(springDocsProperties.getHeader(), new SecurityScheme()
                                .name(springDocsProperties.getHeader())
                                .scheme(springDocsProperties.getScheme())
                                .bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP))
                );
    }
}

```

~~已弃用~~

```java
package com.myss.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web 安全配置
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Security放行SpringDoc
     *
     * @param webSecurity 网络安全
     * @throws Exception 异常
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/swagger-ui/**", "/doc.html", "/v3/api-docs/**");
    }
}
```

```
package com.myss.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurity 5.4.x以上新用法配置
 * 为避免循环依赖，仅用于配置HttpSecurity
 * Created by macro on 2022/5/19.
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * 过滤链
     *
     * @param http HTTP协议
     * @return {@link SecurityFilterChain}
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        /*
          链式编程
          首页所有人都可以访问，功能也只有对应有权限的人才能访问到
          请求授权的规则
         */
        return http
                .authorizeRequests()
                //首页所有人可以访问
//                .antMatchers("/").permitAll()
                //特定权限访问页
                .antMatchers("/swagger-ui/**", "/doc.html", "/v3/api-docs/**").hasRole("doc")
                .and()
                //无权限默认跳转登录页
                .formLogin()
                .and()
                //注销，开启注销功能，跳转到首页
                .logout().logoutSuccessUrl("/")
                .and()
                .build();
    }

    /**
     * 用户认证服务
     *
     * @return {@link UserDetailsService}
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //密码加密
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //设置用户
        UserDetails docUser = User.builder()
                .username("user")
                .password(encoder.encode("123456"))
                //用户权限
                .roles("doc")
                .build();
        return new InMemoryUserDetailsManager(docUser);
    }
}

```

# 六、security



# 七、统一日志代理

jackson配置注解，json化值为null的属性

```
@JsonInclude(JsonInclude.Include.ALWAYS)
```