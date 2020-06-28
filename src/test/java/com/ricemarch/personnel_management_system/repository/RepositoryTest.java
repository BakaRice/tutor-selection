package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
public class RepositoryTest {
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    ElectiveRepository electiveRepository;
    @Autowired
    StudentRepository studentRepo;
    @Autowired
    TeacherRepository teacherRepo;
    @Autowired
    UserRepository userRepository;

//    @Test
    public void test_init() {
        User user = new User();
        user.setName("twt");
        user.setNumber(2004);
        user.setRole(User.Role.STUDENT);
        Student student = new Student();
        student.setUser(user);
        student.setTeacher(teacherRepo.find(1));
        userRepository.save(user);
        studentRepo.save(student);

    }

//    @Test
    public void test_init2() {
        User user = new User();
        user.setName("twt2");
        user.setNumber(2005);
        user.setRole(User.Role.STUDENT);
        Student student = new Student();
        student.setUser(user);
        student.setTeacher(teacherRepo.find(1));
        userRepository.save(user);
        studentRepo.save(student);

    }

//    @Test
    public void test_find_student() {
        Student student = studentRepo.find("twt", 2004);
        log.debug("{}", student.getId());
    }

//    @Test
    public void test_find_teacher() {
        Teacher teacher = teacherRepo.find(1);
        log.debug("{}", teacher.getId());
    }
}
