package cn.lysoy.seckill.service;

import cn.lysoy.seckill.pojo.Goods;
import cn.lysoy.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 方法功能描述：获取商品列表
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     * @return goodsList
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
