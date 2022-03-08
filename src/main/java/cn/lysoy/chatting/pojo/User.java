package cn.lysoy.chatting.pojo;

import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author LySoY
 * @since 2022-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String name;
    private String ticket;
}
