package com.example.rabbitmq.direct;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//表明这是一个配置类
@Configuration
public class RabbitmqDirectConfig {

    //点对点消息队列名称
    public static final String DIRECT_QUEUE = "direct.queue";

    //注入Queue类并创建消息队列
    @Bean
    public Queue directQueue() {
        //第一个参数是队列名字，第二个参数是指是否持久化
        return new Queue(DIRECT_QUEUE, true);
    }

}
