package cn.lysoy.seckill.service;

import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.vo.LoginVo;
import cn.lysoy.seckill.vo.RespBean;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-22
 */
public interface IUserService extends IService<User> {
    
    /**
     * 登陆
     * 
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @param loginVo 登陆信息
     * @return 封装信息
     */
    RespBean doLogin(LoginVo loginVo);
}



