package com.example.rabbitmq.fanout;

import com.example.rabbitmq.Goods;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.rabbitmq.fanout.FanoutConfig.FANOUT_QUEUE1;
import static com.example.rabbitmq.fanout.FanoutConfig.FANOUT_QUEUE2;
import static com.example.rabbitmq.topic.TopicConfig.TOPIC_QUEUE1;
import static com.example.rabbitmq.topic.TopicConfig.TOPIC_QUEUE2;

@Component
public class FanoutConsumer {

    @RabbitListener(queues = FANOUT_QUEUE1)
    public void receiverFanout1(Goods goods){
        System.out.println("receiverFanout1收到的消息为："+goods.toString());
    }

    @RabbitListener(queues = FANOUT_QUEUE2)
    public void receiverFanout2(Goods goods){
        System.out.println("receiverFanout2收到的消息为："+goods.toString());
    }
}
