package com.mint.oauth.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：cw
 * @date ：Created in 2020/8/4 上午9:30
 * @description：
 * @modified By：
 * @version: $
 */


@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean(name = "cloud-auth")
    public Docket createRestApi() {
        //=====添加head参数start============================
        ParameterBuilder parameter1 = new ParameterBuilder();
        ParameterBuilder parameter2 = new ParameterBuilder();
        ParameterBuilder parameter3 = new ParameterBuilder();
        ParameterBuilder parameter4 = new ParameterBuilder();
        ParameterBuilder parameter5 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        parameter1.name("Authorization").description("Authorization令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameter2.name("userId").description("用户Id").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameter3.name("userPhone").description("用户电话").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameter4.name("deviceId").description("设备号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameter5.name("userType").description("用户类型").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(parameter1.build());
        pars.add(parameter2.build());
        pars.add(parameter3.build());
        pars.add(parameter4.build());
        pars.add(parameter5.build());
        // =========添加head参数end===================
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mint.oauth.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars).groupName("cloud-auth"); // 分组

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("auth-接口文档")
                .description("接口文档-鉴权服务")
                .termsOfServiceUrl("http://localhost:8085/auth/doc.html")
                .contact(new Contact("nooyoo", "", ""))
                .version("v1.0")
                .build();
    }

}
