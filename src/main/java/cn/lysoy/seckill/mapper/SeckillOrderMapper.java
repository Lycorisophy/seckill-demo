package cn.lysoy.seckill.mapper;

import cn.lysoy.seckill.pojo.SeckillOrder;
import cn.lysoy.seckill.vo.SeckillOrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
@Mapper
public interface SeckillOrderMapper extends BaseMapper<SeckillOrder> {

    SeckillOrderVo getSeckillOrderVoByOrderId(Long seckillOrderId);

    SeckillOrder getByseckillOrderId(Long seckillOrderId);
}
