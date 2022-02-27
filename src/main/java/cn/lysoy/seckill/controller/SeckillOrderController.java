package cn.lysoy.seckill.controller;


import cn.lysoy.seckill.pojo.Goods;
import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.service.IOrderService;
import cn.lysoy.seckill.service.ISeckillGoodsService;
import cn.lysoy.seckill.service.ISeckillOrderService;
import cn.lysoy.seckill.vo.RespBean;
import cn.lysoy.seckill.vo.RespBeanEnum;
import cn.lysoy.seckill.vo.SeckillOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
@Controller
@RequestMapping("/seckillOrder")
public class SeckillOrderController {
    @Autowired
    private ISeckillOrderService seckillOrderService;

    @RequestMapping("/detail/{seckillOrderId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long seckillOrderId) {
        final SeckillOrder seckillOrder = seckillOrderService.getByseckillOrderId(seckillOrderId);
        if (null == seckillOrder || !seckillOrder.getUserId().equals(user.getId())) {
            return RespBean.error(RespBeanEnum.SECKILL_ORDER_ERROR);
        }
        SeckillOrderVo seckillOrderVo;
        seckillOrderVo = seckillOrderService.getSeckillOrderVoByOrderId(seckillOrder.getId());
        if (null == seckillOrderVo) {
            return RespBean.error(RespBeanEnum.SECKILL_ORDER_ERROR);
        }
        return RespBean.success(seckillOrderVo);
    }
}
