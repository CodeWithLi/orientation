package com.example.orientation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "zw.tencent")
public class FaceIdentifyProperties {
    //生成签名的配置
    private  String secretId;
    private String secretKey;
}
