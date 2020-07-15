package com.example.eureka.serviceinvoke;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//服务名称，指定回退操作
@FeignClient(value = "eureka-service-provider", fallback = PersonClient.PersonClientFallback.class)
public interface PersonClient {

    @GetMapping(value = "/person/{personId}")
    Person findById(@PathVariable("personId") Integer personId);

    @Component
    static class PersonClientFallback implements PersonClient {

        //回退方法
        @Override
        public Person findById(Integer personId) {
            System.out.println("执行findById的回退方法，返回一个预先设定好的Person");
            return new Person(-1, "模拟Person", 0);
        }
    }
}
