package com.ricemarch.personnel_management_system.interceptor;

import com.ricemarch.personnel_management_system.component.RequestComponent;
import com.ricemarch.personnel_management_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StudentInterceptor implements HandlerInterceptor {
    @Autowired
    private RequestComponent requestComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (requestComponent.getRole() != User.Role.STUDENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "无权限");
        }
        return true;
    }
}
