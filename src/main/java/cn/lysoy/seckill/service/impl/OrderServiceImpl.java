package cn.lysoy.seckill.service.impl;

import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.mapper.OrderMapper;
import cn.lysoy.seckill.pojo.SeckillGoods;
import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IOrderService;
import cn.lysoy.seckill.service.ISeckillGoodsService;
import cn.lysoy.seckill.service.ISeckillOrderService;
import cn.lysoy.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public Order seckill(User user, GoodsVo goodsVo) {
        // 秒杀商品表减库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<SeckillGoods>()
                        .eq("goods_id", goodsVo.getId()));
        final Integer stockCount = seckillGoods.getStockCount();
        if (stockCount < 1) {
            return null;
        }
        seckillGoods.setStockCount(stockCount-1);
        seckillGoodsService.updateById(seckillGoods);
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
        if (order.getStatus() != 0){
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
}
