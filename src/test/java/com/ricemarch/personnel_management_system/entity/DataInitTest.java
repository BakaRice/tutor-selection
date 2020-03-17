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

    @Test
    public void test_student_init() {
        // 初始学生
        Student s = new Student(2017214317, "谭文韬");
        Student s1 = new Student(2017225574, "杨乐佳");
        studentRepo.save(s);
        studentRepo.save(s1);
    }

    @Test
    public void test_course_init() {
        Course course = new Course("TestCourse3", 3.0);
        Course course2 = new Course("TestCourse4", 3.5);
        courseRepo.save(course);
        courseRepo.save(course2);
    }

    @Test
    public void test_teacher_init() {
        Teacher teacher = new Teacher("WANGBO", 10);
        Teacher teacher2 = new Teacher("Lili", 9);
        teacherRepo.save(teacher);
        teacherRepo.save(teacher2);
    }

    @Test
    public void test_course_rel_init() {
        Course course = manager.find(Course.class, 3);
        Course course1 = manager.find(Course.class, 4);
        Teacher teacher = manager.find(Teacher.class, 1);
//        Teacher teacher1 = manager.find(Teacher.class, 2);
        course.setTeacher(teacher);
        course1.setTeacher(teacher);
    }

    @Test
    public void test_rel_init() {
        Student s = manager.find(Student.class, 2017214317);
        Course c = manager.find(Course.class, 1);
        Elective elective = new Elective();
        elective.setGrade("A");
        elective.setStudent(s);
        elective.setCourse(c);
        manager.persist(elective);
    }

}
