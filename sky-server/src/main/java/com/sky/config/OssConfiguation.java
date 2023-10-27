package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.config
 * @version: 1.0
 */
@Configuration
@Slf4j
public class OssConfiguation {
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("aliossutil");
        AliOssUtil util = new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getKeyid(), aliOssProperties.getKeysecret(), aliOssProperties.getBucketname());
        return util;
    }
}
