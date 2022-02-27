package cn.lysoy.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类描述：消息发送者
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/26
 */
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSeckillMessage(String message){
        log.info("发送消息"+message);
        rabbitTemplate.convertAndSend("seckillExchange","seckill.message",message);
    }
    /*@Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object msg) {
        log.info("发送消息"+msg);
        rabbitTemplate.convertAndSend("fanoutExchange","",msg);
    }

    public void send01(Object msg) {
        log.info("发送red消息"+msg);
        rabbitTemplate.convertAndSend("directExchange","queue.red",msg);
    }

    public void send02(Object msg) {
        log.info("发送green消息"+msg);
        rabbitTemplate.convertAndSend("directExchange","queue.green",msg);
    }
    public void send03(Object msg) {
        log.info("发送QUEUE1消息"+msg);
        rabbitTemplate.convertAndSend("topicExchange","queue.red.message",msg);
    }

    public void send04(Object msg) {
        log.info("发送QUEUE1和2消息"+msg);
        rabbitTemplate.convertAndSend("topicExchange","message.queue.green.abc",msg);
    }*/
}
