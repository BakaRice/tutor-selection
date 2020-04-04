package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.entity.User;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeacherRepository teacherRepository;

    //用于用户登录校验service
    public User getUser(Integer UserId) {
        return userRepository.find(UserId);
    }


    public Teacher addTeacher(User u, Teacher t) {
     userRepository.save(u);
     t.setUser(u);
     return teacherRepository.save(t);
    }
}
