package cn.lysoy.chatting.service;

import cn.lysoy.chatting.pojo.User;
import cn.lysoy.chatting.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-22
 */
public interface UserService {
    
    /**
     * 登陆
     * 
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param name 用户昵称
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 封装信息
     */
    RespBean doLogin(String name, HttpServletRequest request, HttpServletResponse response);

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
    User getUserByTicket(String ticket, HttpServletRequest request, HttpServletResponse response);

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
    User getUserByCookie(HttpServletRequest request, HttpServletResponse response);
}



