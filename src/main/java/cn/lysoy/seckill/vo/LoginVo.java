package cn.lysoy.seckill.vo;

import cn.lysoy.seckill.validator.IsMobile;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 登陆返回对象
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
    private String password;
}
