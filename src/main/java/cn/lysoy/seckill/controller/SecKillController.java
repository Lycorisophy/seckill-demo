package cn.lysoy.seckill.controller;

import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.service.IOrderService;
import cn.lysoy.seckill.service.ISeckillOrderService;
import cn.lysoy.seckill.vo.GoodsVo;
import cn.lysoy.seckill.vo.RespBean;
import cn.lysoy.seckill.vo.RespBeanEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类描述：秒杀控制
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/24
 */
@Component
@RequestMapping("/seckill")
public class SecKillController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;

    /**
     * 方法功能描述：秒杀
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @param model Model
     * @param user User
     * @param goodsId 前端post的商品ID
     * @return 订单页
     */
    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId) {
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        final GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_ERROR);
            return "secKillFail";
        }
        // 判断是否重复抢购
        final SeckillOrder seckillOrder = seckillOrderService.getOne(
                new QueryWrapper<SeckillOrder>()
                        .eq("user_id", user.getId())
                        .eq("goods_id", goodsId));
        if (seckillOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEAT_ERROR);
            return "secKillFail";
        }
        Order order = orderService.seckill(user, goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVo);
        return "orderDetail";
    }



    /**
     * 方法功能描述：取消秒杀订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     * @param orderId
     * @return 秒杀页面
     */
    @RequestMapping("/cancleOrder")
    public String cancleOrder(Model model, User user, Long orderId){
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        RespBeanEnum msg = RespBeanEnum.CANCLE_ERROR;
        if (orderService.cancleOrder(user, orderId)){
            msg = RespBeanEnum.SUCCESS;
        }
        model.addAttribute("msg", msg);
        return "secKillCancle";
    }

    /**
     * 方法功能描述：支付秒杀订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     * @param orderId
     * @return 秒杀页面
     */
    @RequestMapping("/payOrder")
    public String payOrder(Model model, User user, Long orderId){
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        RespBeanEnum msg = RespBeanEnum.PAY_ERROR;
        if (orderService.payOrder(user, orderId)){
            msg = RespBeanEnum.SUCCESS;
        }
        model.addAttribute("msg", msg);
        return "secKillPaied";
    }
}
