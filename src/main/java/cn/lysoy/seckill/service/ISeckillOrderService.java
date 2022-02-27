package cn.lysoy.seckill.service;

import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.vo.SeckillOrderVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 方法功能描述：通过秒杀订单ID获得秒杀订单详情
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     * @param seckillOrderId 秒杀订单ID
     * @return SeckillOrderVo
     */
    SeckillOrderVo getSeckillOrderVoByOrderId(Long seckillOrderId);

    /**
     * 方法功能描述：通过秒杀订单ID获得秒杀订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     * @param seckillOrderId 秒杀订单ID
     * @return SeckillOrder
     */
    SeckillOrder getByseckillOrderId(Long seckillOrderId);

    /**
     * 方法功能描述：获取秒杀结果
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/27
     * @param user User
     * @param goodsId
     * @return 秒杀订单ID
     */
    Long getResult(User user, Long goodsId);
}
