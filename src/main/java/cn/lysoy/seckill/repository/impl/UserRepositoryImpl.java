package cn.lysoy.seckill.repository.impl;

import cn.lysoy.seckill.pojo.User;
import cn.lysoy.seckill.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;

/**
 * @author lysoy
 */
@Repository("userRepository")
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    @Override
    public boolean save(User user){
        try {
            FileOutputStream fos = new FileOutputStream("user/"+user.getId()+".txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        }catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public User load(Long id) {
      try {
          FileInputStream fis = new FileInputStream("user/"+id+".txt");
          ObjectInputStream ois = new ObjectInputStream(fis);
          return (User)ois.readObject();
      }catch (IOException | ClassNotFoundException ignored) {
      }
        return null;
    }


}
