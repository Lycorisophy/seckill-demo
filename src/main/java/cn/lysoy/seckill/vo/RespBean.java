package cn.lysoy.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 * 
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    /**
     * 状态码
     */
    private long code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回对象
     */
    private Object object;
    
    /**
     * 成功返回结果
     * 
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/22
     * @return RespBean
     */
    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 重载success方法
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/22
     * @param obj 对象
     * @return RespBean
     */
    public static RespBean success(Object obj) {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
    }

    /**
     * 需要直接传参枚举类ResponseBeanEnum，因为成功基本是200，但失败结果有很多
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/22
     * @param respBeanEnum 公共返回对象枚举
     * @return RespBean
     */
    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    /**
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/22
     * @param respBeanEnum 公共返回对象枚举
     * @param obj 对象
     * @return RespBean
     */
    public static RespBean error(RespBeanEnum respBeanEnum, Object obj) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}
