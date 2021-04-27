package com.pzhu.pm.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {

    public static final String TAG_1 = "学生管理";
    public static final String TAG_2 = "学生课程管理";

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .build()
                .tags(new Tag(TAG_1,"学生管理Controller"))
                .tags(new Tag(TAG_2,"学生课程管理Controller"));

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-学生管理系统")
                .description("本文档描述了学生系统接口定义")
                .version("1.0")
                .contact(new Contact("DYQ", "http://www.dyqking.xyz", "987472953@qq.com"))
                .build();
    }
}
