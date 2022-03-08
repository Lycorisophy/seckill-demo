package cn.lysoy.chatting;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class ChattingApplicationTests {
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	void testLock01() {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		final Boolean isLock = valueOperations.setIfAbsent("k1", "v1");
		if(isLock){
			valueOperations.set("name","xxxx");
			String name = (String) valueOperations.get("name");
			// 操作结束，删除锁
			redisTemplate.delete("k1");
		}else {
			log.info("有线程在使用，请稍后再试");
		}
	}

	@Test
	public void testLock02() {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		Boolean isAbsent = valueOperations.setIfAbsent("k1", "v1", 5, TimeUnit.SECONDS);
		if(isAbsent){
			valueOperations.set("name","xxxx");
			String name = (String) valueOperations.get("name");
			log.info("name"+name);
			Integer.parseInt("xxxx");
			// 操作结束，删除锁
			redisTemplate.delete("k1");
		}else {
		    log.info("有线程在使用");
		}
	}

}
