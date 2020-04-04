package com.ricemarch.personnel_management_system.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {
        Profiles profiles = Profiles.of("dev", "test");
        boolean access = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(infoBuild())
                .enable(access)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ricemarch.personnel_management_system.controller"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo infoBuild() {
        Contact contact = new Contact("Tan", "ricemarch.com", "ricemarch@foxmail.com");
        ApiInfo apiInfo = new ApiInfo(
                "导师互选系统API文档",
                "Personel Management System Api Documentation",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
        return apiInfo;
    }


    //通过Swagger2的securitySchemes配置全局参数：如下列代码所示，securitySchemes的ApiKey中增加一个名为“Authorization”，type为“header”的参数。
    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    //在Swagger2的securityContexts中通过正则表达式，
    // 设置需要使用参数的接口（或者说，是去除掉不需要使用参数的接口），
    // 如下列代码所示，通过PathSelectors.regex("^(?!auth).*$")，
    // 所有包含"auth"的接口不需要使用securitySchemes。即不需要使用上文中设置的名为“Authorization”，type为“header”的参数。
    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))//正则表达式 ?!->不匹配  auth
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
//设置完成后进入SwaggerUI，右上角出现“Authorization”按钮，点击即可输入我们配置的参数。
//对于不需要输入参数的接口（上文所述的包含auth的接口），在未输入Authorization参数就可以访问。
//其他接口则将返回401错误。点击右上角“Authorization”按钮，输入配置的参数后即可访问。参数输入后全局有效，无需每个接口单独输入。


}
