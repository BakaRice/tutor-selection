package com.ricemarch.personnel_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

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
                 .build();
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


}
