package com.example.nacosserver;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//通过 Spring Cloud 原生注解 @RefreshScope 实现配置自动更新
@Component
@Data
@RefreshScope
public class NacosConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;
    @Value("${spring.rabbitmq2.port:0}")
    private int port;


}
