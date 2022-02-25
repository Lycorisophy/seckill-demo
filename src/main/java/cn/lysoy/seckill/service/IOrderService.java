package cn.lysoy.seckill.service;

import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
public interface IOrderService extends IService<Order> {

    /**
     * 方法功能描述：秒杀商品，生成订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @param user User
     * @param goodsVo 秒杀商品信息
     * @return 订单
     */
    Order seckill(User user, GoodsVo goodsVo);

    /**
     * 方法功能描述：取消秒杀订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     * @param user User
     * @param orderId 取消订单的ID
     * @return 是否成功取消秒杀订单
     */
    boolean cancleOrder(User user, Long orderId);

    /**
     * 方法功能描述：支付秒杀订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     * @param user User
     * @param orderId 取消订单的ID
     * @return 是否成功取消秒杀订单
     */
    boolean payOrder(User user, Long orderId);

    /**
     * 方法功能描述：根据用户查找用户订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     * @param user User
     * @return ordersList
     */
    List<Order> listByUser(User user);
}
