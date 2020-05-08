package com.example.eureka.servercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//申明是一个Eureka服务器
@EnableEurekaServer
@SpringBootApplication
@RestController
public class EurekaServerCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerCenterApplication.class, args);
    }


    @GetMapping(value = "/test")
    public String Hello() {
        return "Hello, world.";
    }
}
