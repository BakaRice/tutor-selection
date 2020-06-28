package com.ricemarch.personnel_management_system.component;

import com.ricemarch.personnel_management_system.entity.Student;
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
            t.setIntroduction("this is a short introductio! I need 10 students!");
            //set initial quantity
            t.setRanges(10);
            t.setOptional_num(0);
            t.setUser(u);
            userService.addTeacher(u, t);
        }

        int stu_num = 2007;
        User stu_user = userService.getUser(stu_num);
        if (stu_user == null) {
            User user1 = new User();
            user1.setName("initStu");
            user1.setNumber(stu_num);
            user1.setRole(User.Role.STUDENT);
            /** String和CharSequence的关系
             *    String 实现了CharSequence接口
             * public final class String
             *     implements java.io.Serializable, Comparable<String>, CharSequence{ }
             */
            user1.setPassword(encoder.encode(String.valueOf(stu_num)));
            Student student = new Student();
            student.setUser(user1);
            userService.addStudent(user1, student);
        }
    }
}
