package cn.lysoy.chatting.controller;

import cn.lysoy.chatting.pojo.User;
import cn.lysoy.chatting.server.NettyServer;
import cn.lysoy.chatting.service.RoomService;
import cn.lysoy.chatting.service.UserService;
import cn.lysoy.chatting.vo.RespBean;
import cn.lysoy.chatting.vo.RespBeanEnum;
import cn.lysoy.chatting.vo.RoomVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：房间控制
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/03/03
 */
@Controller
@RequestMapping("/room")
@Slf4j
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @Autowired
    private NettyServer nettyServer;

    @RequestMapping(value = "/toList")
    public String toList(Model model, User user) {
        model.addAttribute("user", user);
        List<RoomVo> roomList = roomService.findAll();
        if (null == roomList || roomList.size() <= 0) {
            roomList = new ArrayList<>();
            roomList.add(new RoomVo());
        }
        model.addAttribute("roomList", roomList);
        return "roomList";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public RespBean create(String name, User user) {
        if (roomService.create(name, user)) {
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
    }

    @RequestMapping("/id={id}")
    public String enter(@PathVariable("id") String id, User user, Model model){
        if (null == user){
            user = new User("游客","0");
        }
        RoomVo room = roomService.enter(id, user);
        model.addAttribute("room", room);
        model.addAttribute("username",user.getName());
        try {
            nettyServer.start();
        }
        catch (Exception e) {
            log.error("netty服务启动异常");
        }
        return "room";
    }
}
