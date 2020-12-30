//package com.mint.consume.config;
//
//import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @Author: cw
// * @Date: 2020/12/25 17:25
// * @Description:
// */
//@Component
//public class RequestOriginParserDefinition implements RequestOriginParser {
//
//    @Override
//    public String parseOrigin(HttpServletRequest httpServletRequest) {
//        // 当前 流控应用 放在了请求参数里面，可以放到的地方有很多，比如 参数/请求头/session/等等
//        return httpServletRequest.getParameter("sourceName");
//    }
//}
