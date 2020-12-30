package com.mint.chat.model.wx.publicno.template;

import lombok.Data;

/**
 * @Author: cw
 * @Date: 2020/11/9 14:27
 * @Description:
 */
@Data
public class TemplateBase {

    private String touser;

    private String template_id;

    private String url;

    private TemplateData data;
}

