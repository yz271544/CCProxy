# CCProxy

一个轻量级的HTTP代理服务器，基于Java开发。

## 功能特性

- HTTP代理服务：接收客户端HTTP请求并转发至目标服务器
- 支持HTTP/1.x协议的基本请求与响应解析
- 多线程处理：通过线程池机制处理多个并发连接
- 隧道代理（HTTP CONNECT）：支持HTTPS流量的隧道传输

## 构建项目

使用Maven构建项目：

```
mvn clean package
```

## 运行方式

### 基本运行

```
java -jar target/CCProxy-1.0.jar
```

### 使用外部配置文件

可以使用系统属性指定外部配置文件：

```
java -Dccproxy.config=/path/to/your/config.properties -jar target/CCProxy-1.0.jar
```

其中`config.properties`是一个包含以下配置项的属性文件：

```
# 服务器监听端口
port=8888

# 处理请求的线程数
answer.thread.num=16

# 日志级别
log.level=DEBUG
```

查看项目中的`config-example.properties`文件获取完整示例。