package com.ricemarch.personnel_management_system.entity;

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
public class DataTest {

    @Autowired
    private EntityManager manager;

    @Test
    public void test_student_init() {
        // 初始学生
        Student s = new Student(2017214317, "谭文韬");
        manager.persist(s);
    }

    @Test
    public void test_course_init() {
        //初始课程
        Course c = new Course("计算机网络", 3);
        manager.persist(c);
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
