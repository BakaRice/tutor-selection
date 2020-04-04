package com.ricemarch.personnel_management_system.component;

import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.entity.User;
import com.ricemarch.personnel_management_system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitComponent implements InitializingBean {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        int num = 1001;
        User user = userService.getUser(num);
        if (user == null) {
            User u = new User();
            u.setName("TAN");
            u.setNumber(num);
            u.setRole(User.Role.TEACHER);
            /** String和CharSequence的关系
             *    String 实现了CharSequence接口
             * public final class String
             *     implements java.io.Serializable, Comparable<String>, CharSequence{ }
             */
            u.setPassword(encoder.encode(String.valueOf(num)));

            Teacher t = new Teacher();

            userService.addTeacher(u,t);
        }
    }
}
