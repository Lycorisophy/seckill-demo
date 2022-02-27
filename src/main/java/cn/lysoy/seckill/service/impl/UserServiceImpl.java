package cn.lysoy.seckill.service.impl;

import cn.lysoy.seckill.exception.GlobalException;
import cn.lysoy.seckill.mapper.UserMapper;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.IUserService;
import cn.lysoy.seckill.utils.CookieUtil;
import cn.lysoy.seckill.utils.MD5Util;
import cn.lysoy.seckill.utils.UUIDUtil;
import cn.lysoy.seckill.vo.LoginVo;
import cn.lysoy.seckill.vo.RespBean;
import cn.lysoy.seckill.vo.RespBeanEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 登陆
     *
     * @param loginVo 登陆信息
     * @return 封装信息
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     */
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        // 根据用户名查询用户
        final User user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        // 判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        // 生成Cookile
        String ticket = UUIDUtil.uuid();
        // 将用户信息存入redis
        redisTemplate.opsForValue().set("user:"+ticket, user, 1800, TimeUnit.SECONDS);
        // request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return RespBean.success();
    }

    /**
     * 方法功能描述：根据cookie获取用户信息
     *
     * @param ticket cookie保存的uuid
     * @return User
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     */
    @Override
    public User getUserByCookie(String ticket, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.hasLength(ticket)) {
            return null;
        }
        final User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket",ticket);
        }
        return user;
    }

    /**
     * 方法功能描述：用户更新密码
     *
     * @param ticket   用户凭证
     * @param password 用户想要更新的密码
     * @param request
     * @param response
     * @return RespBean
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     */
    @Override
    public RespBean updatePassword(String ticket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(ticket, request, response);
        if (null != user) {
            throw new GlobalException(RespBeanEnum.USER_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSalt()));
        int result = userMapper.updateById(user);
        if (1 == result) {
            // 删除对应的redis
            redisTemplate.delete("user:"+ticket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.UPDATE_ERROR);
    }
}
