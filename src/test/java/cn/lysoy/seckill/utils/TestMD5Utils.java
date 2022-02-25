package cn.lysoy.seckill.utils;

import org.junit.jupiter.api.Test;

public class TestMD5Utils {
    @Test
    public static void main(String[] args) {
        System.out.println(MD5Util.inputPassToFromPass("password"));
        System.out.println(MD5Util.formPassToDBPass("5149611e1c93ef0a94e8fcd7e00cf0f3","Lycorisophy"));
        System.out.println(MD5Util.inputPassToDBPass("123456", "Lycorisophy"));
    }
}
