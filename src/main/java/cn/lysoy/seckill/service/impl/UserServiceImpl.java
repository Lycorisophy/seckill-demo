package cn.lysoy.seckill.service.impl;

import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.mapper.UserMapper;
import cn.lysoy.seckill.service.IUserService;
import cn.lysoy.seckill.utils.MD5Util;
import cn.lysoy.seckill.utils.ValidatorUtil;
import cn.lysoy.seckill.vo.LoginVo;
import cn.lysoy.seckill.vo.RespBean;
import cn.lysoy.seckill.vo.RespBeanEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;

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
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (!StringUtils.hasLength(mobile) || !StringUtils.hasLength(password)) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }
        // 根据用户名查询用户
        final User user = userMapper.selectById(mobile);
        if (user == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        // 判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}
