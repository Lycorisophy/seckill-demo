package cn.lysoy.seckill.vo;

import cn.lysoy.seckill.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：商品返回对象
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods {
    /**
     * 商品秒杀单价
     */
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    private Integer stockCount;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;
}
