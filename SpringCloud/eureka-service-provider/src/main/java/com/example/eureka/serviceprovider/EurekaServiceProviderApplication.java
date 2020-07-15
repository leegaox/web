package com.example.eureka.serviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Scanner;

//申明为Eureka 客户端
@EnableEurekaClient
@RestController
@SpringBootApplication
public class EurekaServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceProviderApplication.class, args);
    }

    @GetMapping(value = "/person/{personId}")
    public Person Hello(@PathVariable("personId") Integer personId, HttpServletRequest request) {
        Person person = new Person(personId, "lee", 30);
        person.setMessage(request.getServletPath());
        return person;
    }
}
