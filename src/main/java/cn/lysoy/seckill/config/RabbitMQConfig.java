package cn.lysoy.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * 类描述：RabbitMQ配置类
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/26
 */
// @Configuration
public class RabbitMQConfig {
//    private static final String QUEQE01 = "queue_fanout01";
//    private static final String QUEQE02 = "queue_fanout02";
//    private static final String EXCHANGE = "fanoutExchange";
//
//    @Bean
//    public Queue queue() {
//        return new Queue("queue", true);
//    }
//
//    @Bean
//    public Queue queue01() {
//        return new Queue(QUEQE01);
//    }
//    @Bean
//    public Queue queue02() {
//        return new Queue(QUEQE02);
//    }
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(EXCHANGE);
//    }
//    @Bean
//    public Binding binding01(){
//        return BindingBuilder.bind(queue01()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding binding02(){
//        return BindingBuilder.bind(queue02()).to(fanoutExchange());
//    }
}
