package com.example.eureka.serviceinvoke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//让该注解注册为Eureka客户端，以获得服务发现的能力
@EnableEurekaClient
@SpringBootApplication
public class EurekaServiceInvokeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceInvokeApplication.class, args);
    }

    //开启负载均衡
    @LoadBalanced
    @Bean
    public RestTemplate getRestTamplate(){
        return new RestTemplate();
    }


}
