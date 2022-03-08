package cn.lysoy.chatting.service.impl;

import cn.lysoy.chatting.pojo.User;
import cn.lysoy.chatting.service.RoomService;
import cn.lysoy.chatting.vo.RoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<RoomVo> findAll() {
        ValueOperations operations = redisTemplate.opsForValue();
        Set<String> ids;
        ids = (Set<String>) operations.get("roomList");
        if (null==ids || 0 == ids.size()){
            init("临时房间","1");
            ids = (Set<String>) operations.get("roomList");
        }
        List<RoomVo> roomVos = new ArrayList<>();

        for(Iterator<String> it = ids.iterator(); it.hasNext();){
            RoomVo roomVo = (RoomVo) operations.get("room:"+it.next());
            if (null != roomVo){
                roomVo.setLeftTime(System.currentTimeMillis()-roomVo.getCreateTime().getTime()-roomVo.getLeftTime());
                roomVos.add(roomVo);
            }
            else{
                it.remove();
            }
        }
        operations.set("roomList", ids);
        return roomVos;
    }

    @Override
    public boolean create(String name, User user) {
        ValueOperations operations = redisTemplate.opsForValue();
        String id = null;
        if (null == user){
            id = "0";
        }else {
            id = user.getTicket();
        }
        if(null != operations.get("room:"+id)){
            return false;
        }
        RoomVo roomVo = new RoomVo();
        roomVo.setId(id);
        roomVo.setName(name);
        roomVo.setCreateTime(new Date());
        roomVo.setNumber(30);
        roomVo.setLeft(30);
        roomVo.setLeftTime(1800L);
        Set<String> ids;
        ids = (Set<String>) operations.get("roomList");
        if (null==ids){
            init("临时房间","1");
            ids = (Set<String>) operations.get("roomList");
        }
        ids.add(id);
        operations.set("roomList", ids);
        operations.set("room:"+id, roomVo, 1800, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public boolean init(String name, String id) {
        ValueOperations operations = redisTemplate.opsForValue();
        Set<String> ids = new HashSet<>();
        RoomVo roomVo = new RoomVo();
        roomVo.setId(id);
        roomVo.setName(name);
        roomVo.setCreateTime(new Date());
        roomVo.setNumber(30);
        roomVo.setLeft(30);
        roomVo.setLeftTime(-1L);
        ids.add(id);
        operations.set("roomList", ids);
        operations.set("room:"+id, roomVo, 1800, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public RoomVo enter(String id, User user) {
        ValueOperations operations = redisTemplate.opsForValue();
        RoomVo room = (RoomVo) operations.get("room:" + id);
        Integer left = room.getLeft();
        if (null == left || left < 1){
            return null;
        }
        room.setLeft(room.getLeft()-1);
        room.setLeftTime(System.currentTimeMillis()-room.getCreateTime().getTime()-room.getLeftTime());
        operations.setIfAbsent("room:" + id, room, room.getLeftTime(), TimeUnit.SECONDS);
        return room;
    }
}
