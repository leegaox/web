package com.example.rabbitmq.direct;

import com.example.rabbitmq.Goods;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Component
public class DirectProduce {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendDirectQueue() {
        Goods goods = new Goods();
        goods.setGoodsId(111L);
        goods.setGoodsName("测试商品");
        goods.setGoodsIntroduce("这是一个测试商品");
        goods.setGoodsPrice(98.6);
        rabbitTemplate.convertAndSend(RabbitmqDirectConfig.DIRECT_QUEUE, goods);
    }
}
