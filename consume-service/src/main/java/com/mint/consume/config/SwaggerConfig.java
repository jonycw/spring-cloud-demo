package com.mint.consume.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ：cw
 * @date ：Created in 2020/6/16 下午2:56
 * @description：
 * @modified By：
 * @version: $
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.mint.consume.controller"))
                .build()
                .apiInfo(apiInfo())
                .groupName("consume-service").enable(true);
    }
    /*
    .paths(Predicates.or(PathSelectors.ant("/user/add"),
                        PathSelectors.ant("/user/find/*")))
     */

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("mint框架搭建 项目集成 Swagger 实例文档")
                //创建人
                .contact(new Contact("mint", "http://www.baidu.com", ""))
                //版本号
                .version("API V 0.0.1")
                //描述
                .description("API描述")
                .build();
    }
}