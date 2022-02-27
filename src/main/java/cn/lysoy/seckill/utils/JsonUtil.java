package cn.lysoy.seckill.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.util.List;

/**
 * 类描述：Json工具类
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/27
 */
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
      * 将对象转换成json字符串
      *
      * @param obj
      * @return
      */
            public static String object2JsonStr(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
       } catch (JsonProcessingException e) {
            //打印异常信息
            e.printStackTrace();
       }
        return null;
   }
    /**
      * 将字符串转换为对象
      *
      * @param <T> 泛型
      */
            public static <T> T jsonStr2Object(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr.getBytes("UTF-8"), clazz);
       } catch (JsonParseException e) {
            e.printStackTrace();
       } catch (JsonMappingException e) {
            e.printStackTrace();
       } catch (IOException e) {
            e.printStackTrace();
       }
        return null;
   }
    /**
      * 将json数据转换成pojo对象list
      * <p>Title: jsonToList</p>
      * <p>Description: </p>
      *
      * @param jsonStr
  智者乐水 仁者乐山 程序员 乐字节 47 如果需要更多优质的Java、Python、架构、大数据等IT资料请加微信：lezijie007
      * @param beanType
      * @return
      */
            public static <T> List<T> jsonToList(String jsonStr, Class<T> beanType) {
        JavaType javaType =
                objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = objectMapper.readValue(jsonStr, javaType);
            return list;
       } catch (Exception e) {
            e.printStackTrace();
       }
        return null;
   }
}
