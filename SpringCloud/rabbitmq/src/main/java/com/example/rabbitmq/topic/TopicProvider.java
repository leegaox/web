package com.example.rabbitmq.topic;

import com.example.rabbitmq.Goods;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendTopicQueue() {
        Goods goods1 = new Goods();
        goods1.setGoodsId(System.currentTimeMillis());
        goods1.setGoodsName("测试商品1");
        goods1.setGoodsIntroduce("这是第一个测试的商品");
        goods1.setGoodsPrice(98.6);

        Goods goods2 = new Goods();
        goods2.setGoodsId(System.currentTimeMillis());
        goods2.setGoodsName("测试商品2");
        goods2.setGoodsIntroduce("这是二个测试的商品");
        goods2.setGoodsPrice(100.0);

        System.out.println("TopicProvider 已发送消息");

        rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE, "topic.message", goods1);
        rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE, "topic.message2", goods2);

    }

}
