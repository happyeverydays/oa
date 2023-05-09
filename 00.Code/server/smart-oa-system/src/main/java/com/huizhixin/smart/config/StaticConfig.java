package com.huizhixin.smart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.huizhixin.smart.common.util.DySmsHelper;

/**
 * 设置静态参数初始化
 */
@Configuration
public class StaticConfig {

    @Value("${smart.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${smart.sms.accessKeySecret}")
    private String accessKeySecret;

    @Bean
    public void initStatic() {
        DySmsHelper.setAccessKeyId(accessKeyId);
        DySmsHelper.setAccessKeySecret(accessKeySecret);
    }
}
