package com.im2.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by liuyan on 2018/9/7.
 */
@Configuration
public class UserSwaggerConfig {
    @Bean
    public Docket giftApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.im2.users.client"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("用户接口",//标题
                "",
                "1",//版本
                "",
                "刘言",//作者
                "xuey.tk",//链接显示文字
                "https://github.com/liu520yan/IM2"//网站链接
        );

        return apiInfo;
    }
}
