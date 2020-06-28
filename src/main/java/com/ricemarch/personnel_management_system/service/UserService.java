package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.entity.User;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.StudentRepository;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;

    //用于用户登录校验service
    public User getUser(Integer UserNumber) {
          return userRepository.find(UserNumber);
    }


    public Teacher addTeacher(User user, Teacher teacher) {
        //直接用级联 不用再多一层save保存
//     userRepository.save(u);
//     t.setUser(u);
     return teacherRepository.save(teacher);
    }

    public Student addStudent(User user,Student student){
        //直接用级联 不用再多一层save保存
//     userRepository.save(u);
//     t.setUser(u);
        return studentRepository.save(student);
    }
}
