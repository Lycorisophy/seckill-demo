package cn.lysoy.seckill.vo;

import cn.lysoy.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 接受前端登陆页面POST的数据
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/23
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
