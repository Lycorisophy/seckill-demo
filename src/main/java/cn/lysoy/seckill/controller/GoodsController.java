package cn.lysoy.seckill.controller;

import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.service.IUserService;
import cn.lysoy.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * 类描述：商品
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/23
 */
@Component
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    IUserService userService;
    @Autowired
    IGoodsService goodsService;

    /**
     * 方法功能描述：跳转到商品列表页，
     * 验证session,失败则返回登陆，
     * 成功则将信息存入model
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param model Model
     * @param user User
     * @return 商品列表页
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user){
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.findGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goodsList";
    }

    /**
     * 方法功能描述：跳转商品详情页面
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @param model Model
     * @param user User
     * @param goodsId Goods.id
     * @return String
     */
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);
        final Integer stockCount = goodsVo.getStockCount();
        int secKillStatus = 2;
        int remainSeconds = -1;
        if (stockCount > 0){
            Date startDate = goodsVo.getStartDate();
            Date endDate = goodsVo.getEndDate();
            Date currentDate = new Date();
            if (currentDate.before(endDate)) {
                // 秒杀尚未开始
                if (currentDate.before(startDate)) {
                    remainSeconds = (int) ((startDate.getTime() - currentDate.getTime())/1000);
                    secKillStatus = 0;
                } else {
                    // 秒杀正在进行
                    remainSeconds = 0;
                    secKillStatus = 1;
                }
            }
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);
        return "goodsDetail";
    }
}
