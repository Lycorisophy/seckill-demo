package cn.lysoy.seckill.controller;

import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.service.IUserService;
import cn.lysoy.seckill.vo.DetailVo;
import cn.lysoy.seckill.vo.GoodsVo;
import cn.lysoy.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：商品
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/23
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 方法功能描述：跳转到商品列表页，
     * 如果redis中有页面缓存，则取出
     * 没有则存入
     * 不加ResponseBody则无法正常渲染
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param model Model
     * @param user User
     * @param request HttpServerletRequest
     * @param response HttpServerletResponse
     * @return 商品列表页
     */
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user,
                         HttpServletRequest request, HttpServletResponse response) {
        // redis获取页面，如果不为空，获取页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (StringUtils.hasLength(html)){
            return html;
        }
        // 如果为空，渲染并存入redis
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.findGoodsVo();
        model.addAttribute("goodsList", goodsList);
        // return "goodsList";
        WebContext webContext = new WebContext(request, response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine()
                .process("goodsList", webContext);
        if (StringUtils.hasLength(html)) {
            valueOperations.set("goodsList", html,
                    60, TimeUnit.SECONDS);
        }
        return html;
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
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId) {
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
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
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(secKillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);
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
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value="/toDetail/{goodsId}", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String toDetail2(Model model, User user, @PathVariable Long goodsId,
                           HttpServletRequest request, HttpServletResponse response) {
        final String key = "goodsDetail:"+goodsId;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get(key);
        if (StringUtils.hasLength(html)) {
            return html;
        }
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

        WebContext webContext = new WebContext(request, response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine()
                .process("goodsDetail", webContext);
        if (StringUtils.hasLength(html)) {
            valueOperations.set(key, html,
                    60, TimeUnit.SECONDS);
        }
        return html;
    }
}
