package cn.lysoy.seckill.mapper;

import cn.lysoy.seckill.pojo.Goods;
import cn.lysoy.seckill.vo.GoodsVo;
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
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 方法功能描述：获取商品列表
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @return 获取商品列表
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 方法功能描述：获取单个商品信息
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @param goodsId GoodVo.id
     * @return GoodsVo
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
