package cn.lysoy.chatting.controller;

import cn.lysoy.chatting.service.UserService;
import cn.lysoy.chatting.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    UserService userService;

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
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doLogin(String name, HttpServletRequest request, HttpServletResponse response) {
        return userService.doLogin(name, request, response);
    }
}
