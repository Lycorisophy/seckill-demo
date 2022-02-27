package cn.lysoy.seckill.service.impl;

import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.mapper.OrderMapper;
import cn.lysoy.seckill.pojo.SeckillGoods;
import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IOrderService;
import cn.lysoy.seckill.service.ISeckillGoodsService;
import cn.lysoy.seckill.service.ISeckillOrderService;
import cn.lysoy.seckill.utils.MD5Util;
import cn.lysoy.seckill.utils.UUIDUtil;
import cn.lysoy.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 方法功能描述：秒杀商品，生成订单
     *
     * @param user    User
     * @param goodsVo 秒杀商品信息
     * @return 订单
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     */
    @Override
    @Transactional
    public Order seckill(User user, GoodsVo goodsVo) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 秒杀商品表减库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<SeckillGoods>()
                        .eq("goods_id", goodsVo.getId()));
        boolean seckillResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
        .setSql("stock_count = stock_count-1")
        .eq("id", seckillGoods.getId()));
        if(seckillGoods.getStockCount()<1){
            valueOperations.set("isStockEmpty:"+goodsVo.getId(),"0");
            return null;
        }
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        // 生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+seckillGoods.getId(),seckillOrder);
        return order;
    }

    /**
     * 方法功能描述：取消秒杀订单
     *
     * @param user    User
     * @param orderId 取消订单的ID
     * @return 是否成功取消秒杀订单
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     */
    @Override
    public boolean cancleOrder(User user, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order.getStatus() > 1){
            return false;
        }
        if (user.getId().intValue()==order.getUserId().intValue()){
            order.setStatus(4);
            if (SqlHelper.retBool(orderMapper.updateById(order))){
                SeckillGoods seckillGoods = seckillGoodsService.getOne(
                        new QueryWrapper<SeckillGoods>()
                                .eq("goods_id", order.getGoodsId()));
                final Integer stockCount = seckillGoods.getStockCount();
                seckillGoods.setStockCount(stockCount+1);
                seckillGoodsService.updateById(seckillGoods);
                return true;
            }
            else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 方法功能描述：支付秒杀订单
     *
     * @param user    User
     * @param orderId 取消订单的ID
     * @return 是否成功取消秒杀订单
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     */
    @Override
    public boolean payOrder(User user, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order.getStatus() != 0){
            return false;
        }
        if (user.getId().intValue()==order.getUserId().intValue()){
            order.setStatus(1);
            order.setPayDate(new Date());
            if (SqlHelper.retBool(orderMapper.updateById(order))){
                return true;
            }
            else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 方法功能描述：根据用户查找用户订单
     *
     * @param user User
     * @return ordersList
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     */
    @Override
    public List<Order> listByUser(User user) {
        List<Order> orderList = orderMapper.selectByUser(user.getId());
        if (orderList == null || orderList.size() == 0) {
            orderList.add(new Order());
        }
        return orderList;
    }

    /**
     * 方法功能描述：根据用户ID和订单ID进行秒杀
     *
     * @param userId UserId
     * @param goodsId GoodsId
     * @param goodsName
     * @return ordersList
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     */
    @Override
    public Order seckillByUserIdAndGoodsId(Long userId, Long goodsId, String goodsName) {
        SeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<SeckillGoods>()
                        .eq("goods_id", goodsId));
        if (seckillGoods.getStockCount() < 1) {
            return null;
        }
        boolean seckillResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count = stock_count-1")
                .eq("id", seckillGoods.getId()));
        if(!seckillResult){
            return null;
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setGoodsId(goodsId);
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsName);
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        // 生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(userId);
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsId);
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:"+userId+":"+seckillGoods.getId(),seckillOrder);
        return order;
    }

    /**
     * 方法功能描述：获取秒杀地址
     *
     * @param user
     * @param goodsId
     * @return 秒杀地址
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     */
    @Override
    public String createPath(User user, Long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:"+user.getId()+":"+goodsId,str,60, TimeUnit.SECONDS);
        return str;
    }

    /**
     * 方法功能描述：校验秒杀地址
     *
     * @param user
     * @param goodsId
     * @param path
     * @return 秒杀地址是否合规
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     */
    @Override
    public boolean checkPath(User user, Long goodsId,String path) {
        if(null==user||goodsId<0||!StringUtils.hasLength(path)){
            return false;
        }
        String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);
        return path.equals(redisPath);
    }

    /**
     * 方法功能描述：验证码校验
     *
     * @param user
     * @param goodsId
     * @param captcha
     * @return 验证码是否正确
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     */
    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if (!StringUtils.hasLength(captcha) || null == user || goodsId < 0){
            return false;
        }
        String redisCaptcha = (String) redisTemplate.opsForValue().get("captcha:"+user.getId() + ":"+goodsId);
        return captcha.equals(redisCaptcha);
    }
}
