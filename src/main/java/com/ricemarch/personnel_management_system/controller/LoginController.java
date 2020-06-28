package com.ricemarch.personnel_management_system.controller;

import com.ricemarch.personnel_management_system.component.EncryptComponent;
import com.ricemarch.personnel_management_system.component.MyToken;
import com.ricemarch.personnel_management_system.entity.User;
import com.ricemarch.personnel_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class LoginController {

    @Value("${my.teacher}")
    private String roleTeacher;
    @Value("${my.student}")
    private String roleStudent;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EncryptComponent encrypt;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Map login(@RequestBody User login, HttpServletResponse response) {
        if (login==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        if (login.getPassword()==null || login.getNumber()==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码不能为空");
        User user = Optional.ofNullable(userService.getUser(login.getNumber()))
                .filter(u -> encoder.matches(login.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误"));

        MyToken token = new MyToken(user.getId(), user.getRole());
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHORIZATION, auth);//键值对 不要用硬编码 用常量 避免书写错误等情况
        String roleCode = user.getRole() == User.Role.TEACHER ? roleTeacher : roleStudent;
        return Map.of("role", roleCode);
    }
}

