package cn.lysoy.chatting.vo;

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
    LOGIN_ERROR(500210, "用户名已存在"),
    MOBILE_ERROR(500211, "用户名格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),
    UPDATE_ERROR(500213, "更新信息失败"),
    SESSION_ERROR(500214, "用户未登录"),

    // 消息模块返回对象 5005XX
    REQUEST_ILLEGAL(500501, "请求非法"),
    ACCESS_LIMIT(500502, "访问过于频繁，请稍后再试");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态信息
     */
    private final String message;
}
