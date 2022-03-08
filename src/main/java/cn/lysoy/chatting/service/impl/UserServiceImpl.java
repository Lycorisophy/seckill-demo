package cn.lysoy.chatting.service.impl;

import cn.lysoy.chatting.pojo.User;
import cn.lysoy.chatting.service.UserService;
import cn.lysoy.chatting.utils.CookieUtil;
import cn.lysoy.chatting.utils.MD5Util;
import cn.lysoy.chatting.vo.RespBean;
import cn.lysoy.chatting.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-22
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 登陆
     *
     * @param name 登陆信息
     * @return 封装信息
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     */
    @Override
    public RespBean doLogin(String name, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.hasLength(name)) {
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }
        // 生成Cookile
        String ticket = MD5Util.md5(name);
        // 将用户信息存入redis
        ValueOperations valueOperations = redisTemplate.opsForValue();
        final Object o = valueOperations.get("user:" + ticket);
        if (null == o) {
            redisTemplate.opsForValue().set("user:"+ticket, new User(name, ticket), 3600, TimeUnit.SECONDS);
            CookieUtil.setCookie(request,response,"userTicket",ticket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.LOGIN_ERROR);
    }

    /**
     * 方法功能描述：根据cookie获取用户信息
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @param ticket cookie保存的uuid
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return User
     */
    @Override
    public User getUserByTicket(String ticket, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.hasLength(ticket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket",ticket);
        }else {
            user = new User("游客", "0");
            CookieUtil.deleteCookie(request,response,"userTicket");
        }
        return user;
    }

    /**
     * 方法功能描述：根据cookie获取用户信息
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return User
     */
    @Override
    public User getUserByCookie(HttpServletRequest request, HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userTicket")){
                String ticket = cookie.getValue();
                final User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
                return user;
            }
        }
        return null;
    }
}
