package cn.lysoy.seckill.service.impl;

import cn.lysoy.seckill.pojo.Goods;
import cn.lysoy.seckill.mapper.GoodsMapper;
import cn.lysoy.seckill.service.IGoodsService;
import cn.lysoy.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LySoY
 * @since 2022-02-24
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    /**
     * 方法功能描述：获取商品列表
     *
     * @return goodsList
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     */
    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    /**
     * 方法功能描述：获取单个商品信息
     *
     * @param goodsId GoodVo.id
     * @return GoodsVo
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/24
     */
    @Override
    public GoodsVo findGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }
}
