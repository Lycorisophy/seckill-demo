package cn.lysoy.chatting.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString()
public class SocketMessage {
    /**
     * 消息发送者id
     */
    private String userId;
    /**
     * 消息接受者id或群聊id
     */
    private String roomId;
    /**
     * 消息内容
     */
    private String message;
}

