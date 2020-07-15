package com.example.eureka.serviceinvoke;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//开启hystrix的断路器
@EnableCircuitBreaker
//开启Feign客户端配置
@EnableFeignClients
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class FirstFeignInvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstFeignInvokerApplication.class, args);
    }

    @Autowired
    PersonClient personClient;

    @GetMapping(value = "feignRouter")
    public Person router() {
        return personClient.findById(1);
    }
}
