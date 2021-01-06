package com.mint.shiro.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: cw
 * @Date: 2021/1/6 13:55
 * @Description:
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UsersPo implements Serializable {

    private static final long serialVersionUID = -4494187022281572626L;

    private Long id;

    private String username;

    private String password;

    private String salt;

    private String roleId;

    private Boolean locked;

}

