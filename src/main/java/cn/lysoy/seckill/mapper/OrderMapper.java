package cn.lysoy.seckill.mapper;

import cn.lysoy.seckill.pojo.Order;
import cn.lysoy.seckill.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 方法功能描述：根据用户查找用户订单
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/25
     * @param userId UserId
     * @return ordersList
     */
    List<Order> selectByUser(Long userId);
}
