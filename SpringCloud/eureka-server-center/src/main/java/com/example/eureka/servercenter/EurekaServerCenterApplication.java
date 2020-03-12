package com.example.eureka.servercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//申明是一个Eureka服务器
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerCenterApplication.class, args);
    }

}
