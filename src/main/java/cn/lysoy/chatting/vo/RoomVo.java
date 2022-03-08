package cn.lysoy.chatting.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述：房间视图对象
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/03/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomVo implements Serializable {
    private String id;
    private String name;
    private Integer number;
    private Integer left;
    private Date createTime;
    private Long leftTime;
}
