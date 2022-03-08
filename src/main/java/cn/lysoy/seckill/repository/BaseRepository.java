package cn.lysoy.seckill.repository;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author lysoy
 */
public interface BaseRepository<T> {

    default boolean save(T entity) {
        return false;
    }

    T load(Long id);
}
