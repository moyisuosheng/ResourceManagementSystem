# 资源管理系统

# [ResourceManagementSystem]()

# 一、Docker容器部署脚本

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
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://192.168.249.133:9192
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
        - AUTH_TYPE="LOGIN_FORM"
        - SPRING_SECURITY_USER_NAME=admin
        - SPRING_SECURITY_USER_PASSWORD=admin
volumes:
  zookeeper_data:
    driver: local
  kafka_data:
    driver: local
    
```

执行命令：

```sh
docker compose up
```

## Logstash:elk

```sh
docker run -d --restart=always --name elk -p 5602:5601 -p 9201:9200 -p 5044:5044 dockerproxy.cn/sebp/elk:771
```

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

# 二、Docker + idea docker插件实现远程部署项目

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

# 二、Logstash

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

# 三、SpringDoc

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

# 四、security

# 五、统一日志代理

jackson配置注解，json化值为null的属性

```
@JsonInclude(JsonInclude.Include.ALWAYS)
```