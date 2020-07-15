package com.example.rabbitmq.topic;

import com.example.rabbitmq.Goods;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.rabbitmq.topic.TopicConfig.TOPIC_QUEUE1;
import static com.example.rabbitmq.topic.TopicConfig.TOPIC_QUEUE2;

@Component
public class TopicConsumer {

    //消费者1只能收到其中的一条信息
    @RabbitListener(queues = TOPIC_QUEUE1)
    public void receiverTopic1(Goods goods) {
        System.out.println("receiverTopic1收到的消息为：" + goods.toString());
    }

    //消费者2能收到生产者发送的两条信息
    @RabbitListener(queues = TOPIC_QUEUE2)
    public void receiverTopic2(Goods goods) {
        System.out.println("receiverTopic2收到的消息为：" + goods.toString());
    }
}
