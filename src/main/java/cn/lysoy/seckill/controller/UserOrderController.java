package cn.lysoy.seckill.controller;

import cn.lysoy.seckill.pojo.Goods;
import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.service.IOrderService;
import cn.lysoy.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 类描述：个人订单管理
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/25
 */
@Component
@RequestMapping("/userOrder")
public class UserOrderController {
    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IGoodsService goodsService;

    /**
     * 方法功能描述：跳转到个人订单列表页，
     * 验证session,失败则返回登陆，
     * 成功则将信息存入model
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param model Model
     * @param user User
     * @return 订单列表页
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user){
        model.addAttribute("user", user);
        List<Order> orderList = orderService.listByUser(user);
        if (orderList.size() > 0) {
            model.addAttribute("orderList", orderList);
            return "userOrderList";
        }
        return "goodsList";
    }

    /**
     * 方法功能描述：跳转订单详情页面
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     * @param model Model
     * @param user User
     * @param orderId Order.id
     * @return String
     */
    @RequestMapping("/toDetail/{orderId}")
    public String toDetail(Model model, User user, @PathVariable Long orderId) {
        model.addAttribute("user", user);
        final Order order = orderService.getById(orderId);
        final Goods goods = goodsService.getById(order.getGoodsId());
        model.addAttribute("goods", goods);
        model.addAttribute("order", order);
        return "userOrderDetail";
    }
}
