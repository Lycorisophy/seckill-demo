package cn.lysoy.seckill.service.impl;

import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.mapper.SeckillOrderMapper;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.service.ISeckillOrderService;
import cn.lysoy.seckill.vo.SeckillOrderVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 方法功能描述：通过秒杀订单ID获得秒杀订单详情
     *
     * @param seckillOrderId 秒杀订单ID
     * @return SeckillOrderVo
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     */
    @Override
    public SeckillOrderVo getSeckillOrderVoByOrderId(Long seckillOrderId) {
        return seckillOrderMapper.getSeckillOrderVoByOrderId(seckillOrderId);
    }

    /**
     * 方法功能描述：通过秒杀订单ID获得秒杀订单
     *
     * @param seckillOrderId 秒杀订单ID
     * @return SeckillOrder
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     */
    @Override
    public SeckillOrder getByseckillOrderId(Long seckillOrderId) {
        return seckillOrderMapper.getByseckillOrderId(seckillOrderId);
    }

    /**
     * 方法功能描述：获取秒杀结果
     *
     * @param user    User
     * @param goodsId
     * @return 秒杀订单ID
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if (null != seckillOrder) {
            return seckillOrder.getOrderId();
        }else if(redisTemplate.hasKey("isStockEmpty:"+goodsId)){
            return -1L;
        }else {
            return 0L;
        }

    }
}
