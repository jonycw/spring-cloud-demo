package com.mint.gateway.filter;

import com.mint.gateway.provider.SwaggerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ：cw
 * @date ：Created in 2020/8/3 下午5:51
 * @description：
 * @modified By：
 * @version: $
 */

@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {

    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    private static final String HOST_NAME = "X-Forwarded-Host";
    private Logger log = LoggerFactory.getLogger(SwaggerHeaderFilter.class);
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            log.info("请求路径:{}",path);
            if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
                return chain.filter(exchange);
            }
            String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));
            log.info("base请求路径:{}",path);
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);

        };

    }
}