package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.aliyun.oss.file")
@Data
public class AliOssProperties {

    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;

}
