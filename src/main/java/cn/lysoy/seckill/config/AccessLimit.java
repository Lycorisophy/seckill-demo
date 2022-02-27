package cn.lysoy.seckill.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述：限制访问
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int second() default 60;
    int maxCount() default 100;
    boolean needLogin() default false;
}
