package cn.lysoy.seckill.validator;

import cn.lysoy.seckill.vo.IsMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类描述：验证手机号
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/23
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {
    boolean required() default true;

    String message() default "手机号码校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
