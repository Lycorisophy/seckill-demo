package cn.lysoy.seckill.repository;

import cn.lysoy.seckill.pojo.User;

public interface UserRepository extends BaseRepository<User>{
    @Override
    boolean save(User user);
    @Override
    User load(Long id);
}
