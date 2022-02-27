package cn.lysoy.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述：秒杀信息
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {
    private User user;
    private Long goodsId;
}
