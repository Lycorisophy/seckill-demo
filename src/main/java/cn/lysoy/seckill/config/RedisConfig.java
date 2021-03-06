package cn.lysoy.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 类描述：redis控制类
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/24
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // key的序列化
        template.setKeySerializer(new StringRedisSerializer());
        // value的序列化
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hash类型 key的序列化
        template.setHashKeySerializer(new StringRedisSerializer());
        // hash类型 value的序列化
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 注入连接工厂
        template.setConnectionFactory(factory);
        return template;
    }
}
