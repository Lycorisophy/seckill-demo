package cn.lysoy.seckill.controller;

import cn.lysoy.seckill.service.IUserService;
import cn.lysoy.seckill.vo.LoginVo;
import cn.lysoy.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登陆控制
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/22
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    IUserService userService;

    /**
     * 跳转登陆页面
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登陆方法
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param
     * @return
     */
    @RequestMapping("/doLogin")
    /**将controller的方法返回的对象 通过适当的转换器
     * 转换为指定的格式之后，写入到response对象的body区（响应体中），
     * 通常用来返回JSON数据或者是XML
     */
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        return userService.doLogin(loginVo, request, response);
    }
}
