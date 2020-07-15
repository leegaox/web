package com.example.eureka.serviceinvoke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/router")
    public String router() {
        //在这里不像是http://ip:端口号这种书写方式，而是直接采用 服务名/方法名来直接调用方法，因为你不知道具体的方法在哪个ip的机器上，
        // 需要由Eureka进行调用，这样消费者就不用关心由谁提供了服务，只要提供了服务即可，这也是面向对象方法的一种体现，同时也能很好的解耦。
        String json = restTemplate.getForObject("http://first-service-provider/person/1", String.class);
        return json;
    }
}
