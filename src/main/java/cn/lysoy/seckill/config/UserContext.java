package cn.lysoy.seckill.config;

import cn.lysoy.seckill.pojo.User;

/**
 * 类描述：
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/27
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();
    public static void setUser(User user) {
        userHolder.set(user);
    }
    public static User getUser() {
        return userHolder.get();
    }
}
