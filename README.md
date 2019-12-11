# 加密方式

 https://szsctc.github.io/sztc_enc/

## 使用方式

调用代码的方式:

1. 引入代码

```xml
<dependency>
  <groupId>cn.szsctc</groupId>
  <artifactId>sctc_enc</artifactId>
  <version>1.0.1</version>
</dependency>
```


2 代码调用方式:

    ```java
        App app = App.instance();
        // 原始数据
        byte[] data = "data".getBytes("utf-8");
        // 设备编号获取密钥进行加密
        byte[] encData = app.enc( app.key( "SN001"), data );
    ```

## 版本更新

- 1.0.2 同步到服务端数据
- 1.0.1 添加设备密钥
- 1.0.0 同步到中央仓库
