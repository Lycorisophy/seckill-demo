package cn.lysoy.seckill.vo;

import cn.lysoy.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
    private Long orderId;
    private boolean isPaid;
}
