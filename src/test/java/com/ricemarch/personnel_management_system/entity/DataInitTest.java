package com.ricemarch.personnel_management_system.entity;

import com.ricemarch.personnel_management_system.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
public class DataInitTest {

    @Autowired
    private EntityManager manager;
    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private StudentRepository studentRepo;


//    @Test
    public void test_course_init() {
        Course course = new Course("TestCourse3", 3.0);
        Course course2 = new Course("TestCourse4", 3.5);
        courseRepo.save(course);
        courseRepo.save(course2);
    }

//    @Test
    public void test_teacher_init() {
        Teacher teacher = new Teacher("WANGBO", 10);
        Teacher teacher2 = new Teacher("Lili", 9);
        teacherRepo.save(teacher);
        teacherRepo.save(teacher2);
        teacherRepo.refresh(teacher);
        teacherRepo.refresh(teacher2);
    }

//    @Test
    public void test_course_rel_init() {
        Course course = manager.find(Course.class, 1);
        Course course1 = manager.find(Course.class, 2);
        Teacher teacher = manager.find(Teacher.class, 1);
//        teacherRepo.refresh(teacher);
//        Teacher teacher1 = manager.find(Teacher.class, 2);
        course.setTeacher(teacher);
        course1.setTeacher(teacher);
        manager.persist(course);
    }

//    @Test
    public void test_rel_init() {
        Student s = manager.find(Student.class, 2017214317);
        Course c = manager.find(Course.class, 1);
        Elective elective = new Elective();
        elective.setGrade((float) 80);
        elective.setStudent(s);
        elective.setCourse(c);
        elective.setStudent(studentRepo.findById(2017214317).orElse(null));
        manager.persist(elective);
    }

}
