package com.mint.chat.model.wx.publicno;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: cw
 * @Date: 2020/11/9 14:58
 * @Description:
 */
@Data
public class AccessTokenDto implements Serializable {

    private static final long serialVersionUID = -4591243067274380744L;

    private String access_token;

    private Long expires_in;

    private String openid;

}

