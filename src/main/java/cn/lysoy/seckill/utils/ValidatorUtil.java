package cn.lysoy.seckill.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：手机号码等校验
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/23
 */
public class ValidatorUtil {
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");

    public static boolean isMobile(String mobile) {
        if (!StringUtils.hasLength(mobile)) {
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();
    }
}
