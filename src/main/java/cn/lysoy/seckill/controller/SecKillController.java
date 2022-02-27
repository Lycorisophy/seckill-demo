package cn.lysoy.seckill.controller;

import cn.lysoy.seckill.config.AccessLimit;
import cn.lysoy.seckill.exception.GlobalException;
import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.pojo.SeckillMessage;
import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.rabbitmq.MQSender;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.service.IOrderService;
import cn.lysoy.seckill.service.ISeckillOrderService;
import cn.lysoy.seckill.utils.JsonUtil;
import cn.lysoy.seckill.vo.GoodsVo;
import cn.lysoy.seckill.vo.RespBean;
import cn.lysoy.seckill.vo.RespBeanEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：秒杀控制
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/24
 */
@Component
@RequestMapping("/seckill")
@Slf4j
public class SecKillController implements InitializingBean {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;

    private Map<Long, Boolean> emptyStockMap = new HashMap<>();

    /**
     * 方法功能描述：秒杀
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @param user User
     * @param goodsId 前端post的商品ID
     * @return 订单页
     */
    @RequestMapping(value="/{path}/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(@PathVariable String path, User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        // 判断是否重复抢购
        ValueOperations valueOperations = redisTemplate.opsForValue();
        boolean check = orderService.checkPath(user,goodsId,path);
        if (!check) {
            return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
        }
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (null != seckillOrder) {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        // 通过内存标记减少redis访问
        if (emptyStockMap.get(goodsId)) {
            return RespBean.error(RespBeanEnum.EMPTY_ERROR);
        }
        String goodsKey = "seckillGoods:" + goodsId;
        Long stock = valueOperations.decrement(goodsKey);
        if (stock < 0) {
            emptyStockMap.put(goodsId, true);
            valueOperations.increment(goodsKey);
            return RespBean.error(RespBeanEnum.EMPTY_ERROR);
        }
        // GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        // Order order = orderService.seckillByUserIdAndGoodsId(user.getId(), goodsId, goodsVo.getGoodsName());
        // Order order = orderService.seckill(user, goodsVo);
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.success(0);
        /*final GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount()<1){
            return RespBean.error(RespBeanEnum.EMPTY_ERROR);
        }
        // 判断是否重复抢购
        //        final SeckillOrder seckillOrder = seckillOrderService.getOne(
        //                new QueryWrapper<SeckillOrder>()
        //                        .eq("user_id", user.getId())
        //                        .eq("goods_id", goodsId));
        final SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        Order order = orderService.seckill(user, goodsVo);
        return RespBean.success(order);*/
    }

    @RequestMapping(value = "/captcha",method = RequestMethod.GET)
    public void verifyCode(User user, Long goodsId, HttpServletResponse response){
        if(null==user||goodsId<0){
            throw new GlobalException(RespBeanEnum.REQUEST_ILLEGAL);
        }
        // 设置请求头为输出图片类型
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 32,3);
        redisTemplate.opsForValue().set("captcha:"+user.getId() + ":"+goodsId,
                captcha.text(),300, TimeUnit.SECONDS);
        try {
            captcha.out(response.getOutputStream());
        }catch (IOException e) {
            log.error("验证码生成失败"+e.getMessage());
        }
    }

/*
    */
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
     *//*

    @RequestMapping("/doSeckill")
    public String doSeckill2(Model model, User user, Long goodsId) {
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
*/



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
        RespBeanEnum respBeanEnum = RespBeanEnum.PAY_ERROR;
        if (orderService.payOrder(user, orderId)){
            respBeanEnum = RespBeanEnum.SUCCESS;
        }
        model.addAttribute("msg", respBeanEnum);
        return "secKillPaied";
    }

    /**
     * 方法功能描述：获取秒杀结果
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     * @param user User
     * @return orderId:success;-1:fail;0:排队ing
     */
    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user, Long goodsId){
        if (null == user){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }

    /**
     * 方法功能描述：获取秒杀地址
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     * @param user USER
     * @param goodsId
     * @param captcha 用户输入的验证码
     * @param request
     * @return RespBean
     */
    @AccessLimit(second=5, maxCount=5, needLogin = true)
    @RequestMapping(value = "/path",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getPath(User user, Long goodsId,String captcha, HttpServletRequest request){
        if (null == user) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        if (null == captcha){
            return RespBean.error(RespBeanEnum.CAPTCHA_ERROR);
        }
        boolean check = orderService.checkCaptcha(user,goodsId,captcha);
        if (!check) {
            return RespBean.error(RespBeanEnum.CAPTCHA_ERROR);
        }
        String str = orderService.createPath(user,goodsId);
        return RespBean.success(str);
    }
    /**
     * 方法功能描述：初始化时，将商品库存数量加载到Redis
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(goodsVoList)) {
            return;
        }
        goodsVoList.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(), goodsVo.getStockCount());
            emptyStockMap.put(goodsVo.getId(),false);
        });
    }
}
