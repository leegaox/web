package com.example.nacosserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/config")
@SpringBootApplication
public class NacosServerApplication {

    @Autowired
    NacosConfigController config;

    public static void main(String[] args) {
        SpringApplication.run(NacosServerApplication.class, args);
    }


    @GetMapping(value = "/get")
    public String get() {
        return config.toString();
    }
}
