package com.mint.chat.model.wx.publicno;

import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * @Author: cw
 * @Date: 2020/12/30 12:11
 * @Description:
 */

@Data
public class UserInfoDto implements Serializable {

    private String openId;

    private String nickName;

    private String sex;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private String[] privilege;

    private String unionid;

}

