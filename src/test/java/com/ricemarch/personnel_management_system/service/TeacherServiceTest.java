package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.service.impl.TeacherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class TeacherServiceTest {

    @Autowired
    TeacherServiceImpl teacherService;


//    @Test
//    @Transactional
//    @Rollback(value = false)
    public void test_remove_electives() {
        log.debug("{}",  teacherService.removeCourseStudents(2) );
    }

//    @Test
    public void test_teacher_updateRange() {
//        Teacher update = teacherService.update(1, 10, 100);
//        log.debug("{}", update.getRanges());
    }

//    @Test
    public void test_teacher_updateInfo() {
        Teacher update = teacherService.update(1, "BOO", "I'M BOO");
        log.debug("{}", update.getIntroduction());
    }

}
