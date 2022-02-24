package cn.lysoy.seckill.service.impl;

import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.mapper.OrderMapper;
import cn.lysoy.seckill.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
