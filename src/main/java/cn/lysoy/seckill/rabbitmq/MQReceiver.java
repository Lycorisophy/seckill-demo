package cn.lysoy.seckill.rabbitmq;

import cn.lysoy.seckill.pojo.SeckillMessage;
import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.service.IOrderService;
import cn.lysoy.seckill.utils.JsonUtil;
import cn.lysoy.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * 类描述：消息消费者
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/26
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;
    /**
     * 方法功能描述：下单功能
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     * @param message
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message){
        log.info("接收的消息"+message);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if(goodsVo.getStockCount()<1){
            return;
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (null != seckillOrder) {
            return;
        }
        // 下单操作
        orderService.seckill(user, goodsVo);
    }
    /*@RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收消息:"+msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void receive01(Object msg) {
        log.info("QUEUE01接受消息"+msg);
    }
    @RabbitListener(queues = "queue_fanout02")
    public void receive02(Object msg) {
        log.info("QUEUE02接受消息"+msg);
    }
    @RabbitListener(queues = "queue_direct01")
    public void receive03(Object msg) {
        log.info("QUEUE01接受消息"+msg);
    }
    @RabbitListener(queues = "queue_direct02")
    public void receive04(Object msg) {
        log.info("QUEUE02接受消息"+msg);
    }
    @RabbitListener(queues = "queue_topic01")
    public void receive05(Object msg) {
        log.info("QUEUE01接受消息"+msg);
    }
    @RabbitListener(queues = "queue_topic02")
    public void receive06(Object msg) {
        log.info("QUEUE02接受消息"+msg);
    }*/
}
