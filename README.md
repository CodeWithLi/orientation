# 大学智能迎新系统

基于 Java SpringBoot 的项目

[toc]

## 模板特点

### 主流框架 & 特性

- Spring Boot 
- Spring MVC
- MyBatis（开启分页）
- Spring Scheduler 定时任务

### 数据存储

- MySQL 数据库
- Redis 内存数据库
- Cache Spring 缓存
- Minio 对象存储

### 工具类

- POI Excel 表格处理
- Hutool 工具库
- Apache Commons Lang3 工具类
- Lombok 注解
- Validation 验证
- Security Crypto 加密工具
- 人脸识别 等等。。

### 业务特性

- JWT身份验证和授权
- 全局请求响应拦截器
- 全局异常处理器
- 自定义错误码
- 封装通用响应类
- 全局跨域处理
- 多环境配置


## 业务功能

- 管理员用户管理：包括登录、展示、添加、删除、修改信息。
- 后台学生信息管理：查询、分页、添加、修改、删除、导入、统计
- 后台广告管理：查询、分页、添加、修改、删除、发布等等。
- 后台任务管理：查询、增加、编辑、删除、修改发布状态、上传图片、审核。
- 移动端学生注册、发送手机验证码、登录
- 移动端排行榜展示及点赞
- 人脸识别、任务自动审核
- 帖子发布、获取关注信息
- 支持文件上传

### 单元测试

- 基于apifox进行接口测试
- apifox地址：https://apifox.com/apidoc/shared-c83623a2-483f-4b33-9f89-6080d632f65b

### 架构设计

- 合理分层


### MySQL 数据库

1）修改 `application.yml` 的数据库配置为你自己的：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456
```

2）执行 `sql/orientation.sql` 中的数据库语句，自动创建库表


### Redis 缓存

1）修改 `application.yml` 的 Redis 配置为你自己的：

```yml
spring:
  redis:
    host: localhost
    port: 6379
    password: 123456
```

### minio 对象存储

1）修改 `application.yml` 的 minion 配置为你自己的：
```yml
endpoint: http://localhost:9000
bucketName: orientation
accessKey: 
secretKey: 
```

### tencent密钥
```yml
    # id
    secret-id: 
    # 密钥
    secret-key:
```

###  uni短信服务
```yml
    #短信公钥
    access-key-id: 
    #短信模板 ID
    template_id:
    #短信签名
    signature: 
    #有效时间 1000*60*3
    ttl: 
```

运行OrientationApplication即可访问，端口为3080
服务器地址: 114.132.67.226:3080