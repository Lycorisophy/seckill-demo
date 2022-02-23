package cn.lysoy.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * MD5工具类
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/22
 */
@Component
public class MD5Util {
    private static final String SALT = "Lycorisophy";
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String inputPassToFromPass(String inputPass) {
        String str = ""+SALT.charAt(0)+SALT.charAt(3) + inputPass + SALT.charAt(5)+SALT.charAt(2);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(3) + formPass + salt.charAt(5)+salt.charAt(2);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        String formPass = inputPassToFromPass(inputPass);
        return formPassToDBPass(formPass, salt);
    }
}
