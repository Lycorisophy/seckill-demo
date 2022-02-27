package cn.lysoy.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：秒杀订单详情Vo
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrderVo {
    /**
     * 订单名称
     */
    private Long orderId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 商品秒杀单价
     */
    private BigDecimal seckillPrice;
    /**
     * 订单创建的时间
     */
    private Date createDate;
    /**
     * 订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
     */
    private Integer status;
    /**
     * 用户ID，手机号码
     */
    private Long userId;

    /**
     * 收货人昵称
     */
    private String nickname;
    /**
     * 收货地址ID
     */
    private Long deliveryAddrId;
}
