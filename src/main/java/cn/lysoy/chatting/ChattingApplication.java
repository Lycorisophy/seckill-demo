package cn.lysoy.chatting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 类描述：聊天室服务启动类
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/03/03
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class ChattingApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChattingApplication.class, args);
//		try {
//			new NettyServer(9123).start();
//		} catch (Exception e) {
//			log.error("NettyServerError:" + e.getMessage());
//		}
	}
}
