package com.ricemarch.personnel_management_system.config;

import com.ricemarch.personnel_management_system.interceptor.LoginInterceptor;
import com.ricemarch.personnel_management_system.interceptor.StudentInterceptor;
import com.ricemarch.personnel_management_system.interceptor.TeacherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private TeacherInterceptor teacherInterceptor;

    @Autowired
    private StudentInterceptor studentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login");

        registry.addInterceptor(teacherInterceptor)
                .addPathPatterns("/api/teachers/**");
        registry.addInterceptor(studentInterceptor)
                .addPathPatterns("/api/students/**");
    }
}
