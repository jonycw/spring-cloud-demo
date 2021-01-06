package com.mint.shiro.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: cw
 * @Date: 2021/1/6 14:10
 * @Description:
 */

@Data
@AllArgsConstructor
@Builder
public class PermissionsPo implements Serializable {
    private static final long serialVersionUID = -3879825860478880434L;

    private Long id;

    private String permission;

    private String description;

    private Long rid;

    private Boolean available;

}

