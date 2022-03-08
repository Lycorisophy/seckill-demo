package cn.lysoy.seckill.vo;

import cn.lysoy.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述：商品详情返回对象
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int secKillStatus;
    private int remainSeconds;
}
