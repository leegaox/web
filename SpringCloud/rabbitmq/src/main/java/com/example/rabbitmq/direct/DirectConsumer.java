package com.example.rabbitmq.direct;

import com.example.rabbitmq.Goods;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.rabbitmq.direct.RabbitmqDirectConfig.DIRECT_QUEUE;

/**
 * 消息接收者
 */
@Component
public class DirectConsumer {

    @RabbitListener(queues = DIRECT_QUEUE)
    public void receiveDirectQueue(Goods goods) {
        System.out.println("收到的消息为：" + goods.toString());
    }


}
