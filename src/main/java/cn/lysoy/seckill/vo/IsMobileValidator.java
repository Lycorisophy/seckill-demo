package cn.lysoy.seckill.vo;

import cn.lysoy.seckill.utils.ValidatorUtil;
import cn.lysoy.seckill.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * 类描述：校验手机号的规则
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/23
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isMobile(s);
        } else {
            if (!StringUtils.hasText(s)){
                return true;
            }else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
