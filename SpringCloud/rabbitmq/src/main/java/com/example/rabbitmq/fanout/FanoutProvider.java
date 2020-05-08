package com.example.rabbitmq.fanout;

import com.example.rabbitmq.Goods;
import com.example.rabbitmq.topic.TopicConfig;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendFanoutQueue(){
        Goods goods1 = new Goods();
        goods1.setGoodsId(System.currentTimeMillis());
        goods1.setGoodsName("测试商品1");
        goods1.setGoodsIntroduce("这是第一个测试的商品");
        goods1.setGoodsPrice(98.6);

        System.out.println("FanoutProvider 已发送消息");

        rabbitTemplate.convertAndSend(FanoutConfig.FANOUT_EXCHANGE, "",goods1);
    }
}
