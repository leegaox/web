package cn.lee.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    public String getJdbcUrl() {
        return jdbcUrl;
    }
}
