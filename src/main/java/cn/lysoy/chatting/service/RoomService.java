package cn.lysoy.chatting.service;

import cn.lysoy.chatting.pojo.User;
import cn.lysoy.chatting.vo.RoomVo;

import java.util.List;

public interface RoomService {
    List<RoomVo> findAll();

    boolean create(String name, User user);

    boolean init(String name, String id);

    RoomVo enter(String id, User user);
}
