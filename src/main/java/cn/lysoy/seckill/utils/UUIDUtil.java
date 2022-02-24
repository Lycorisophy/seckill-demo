package cn.lysoy.seckill.utils;

import java.util.UUID;

/**
 * 类描述：UUID工具类
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/23
 */
public class UUIDUtil {

    /**
     * 方法功能描述：生成Uuid
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/23
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
