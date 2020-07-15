package com.example.rabbitmq;

import com.example.rabbitmq.direct.DirectProduce;
import com.example.rabbitmq.fanout.FanoutProvider;
import com.example.rabbitmq.topic.TopicProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RabbitmqApplication {

    @Autowired
    public DirectProduce directProduce;

    @Autowired
    public TopicProvider topicProvider;

    @Autowired
    public FanoutProvider fanoutProvider;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }

    @GetMapping(value = "directTest")
    public String directTest() {
        directProduce.sendDirectQueue();
        return "true";
    }

    @GetMapping(value = "topicTest")
    public void topicTest() {
        topicProvider.sendTopicQueue();
    }

    @GetMapping(value = "fanoutTest")
    public void fanoutTest() {
        fanoutProvider.sendFanoutQueue();
    }

}
