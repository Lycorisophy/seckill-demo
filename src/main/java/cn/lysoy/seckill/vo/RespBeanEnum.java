package cn.lysoy.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 公共返回对象枚举
 * 
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/22
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    // 通用返回对象
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),

    // 登录模块返回对象 5002XX
    LOGIN_ERROR(500210, "用户名或密码不正确"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),

    // 秒杀模块返回对象 5005XX
    EMPTY_ERROR(500500, "库存不足"),
    REPEAT_ERROR(500501,"重复抢购，该商品每人限购一件");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态信息
     */
    private final String message;
}
