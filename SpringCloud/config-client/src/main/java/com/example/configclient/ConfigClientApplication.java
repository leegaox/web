package com.example.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//启动refresh机制，在执行/refresh时会更新该注解标注类下的所有变量值 和
@RefreshScope
@RestController
@SpringBootApplication
public class ConfigClientApplication {

    @Value("${clientParam}")
    private String clientParam;

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @GetMapping(value = "clientParam")
    public String getParam(){
        return this.clientParam;
    }

}
