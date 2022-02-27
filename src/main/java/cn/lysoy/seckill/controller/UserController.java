package cn.lysoy.seckill.controller;


import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.rabbitmq.MQSender;
import cn.lysoy.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LySoY
 * @since 2022-02-22
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /*@Autowired
    private MQSender mqSender;

    *//**
     * 方法功能描述：用户信息（测试）
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     * @param user User
     * @return RespBean
     *//*
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }

    *//**
     * 方法功能描述：消息发送（测试）
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     *//*
    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("Hello World!");
    }

    *//**
     * 方法功能描述：Fanout模式
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     *//*
    @RequestMapping("/mq/fanout")
    @ResponseBody
    public void mq01(){
        mqSender.send("Hello");
    }

    *//**
     * 方法功能描述：Direct01模式
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     *//*
    @RequestMapping("/mq/direct01")
    @ResponseBody
    public void mq02(){
        mqSender.send01("Hello,RED");
    }
    *//**
     * 方法功能描述：Direct02模式
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     *//*
    @RequestMapping("/mq/direct02")
    @ResponseBody
    public void mq03(){
        mqSender.send02("Hello,GREEN");
    }
    *//**
     * 方法功能描述：Topic01模式
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     *//*
    @RequestMapping("/mq/topic01")
    @ResponseBody
    public void mq04(){
        mqSender.send03("Hello,RED");
    }
    *//**
     * 方法功能描述：Topic02模式
     *
     * @author: lysoy
     * @email: s2295938761@163.com
     * @date: 2022/02/26
     *//*
    @RequestMapping("/mq/topic02")
    @ResponseBody
    public void mq05(){
        mqSender.send04("Hello,GREEN");
    }
*/
}
