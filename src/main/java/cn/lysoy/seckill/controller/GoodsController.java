package cn.lysoy.seckill.controller;

import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 方法功能描述：跳转到商品列表页，
     * 验证session,失败则返回登陆，
     * 成功则将信息存入model
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param model Model
     * @return 商品列表页
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user){
        model.addAttribute("user", user);
        return "goodsList";
    }
}
