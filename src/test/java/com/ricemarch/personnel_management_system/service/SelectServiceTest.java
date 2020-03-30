package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.service.impl.SelectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class SelectServiceTest {

    @Autowired
    SelectServiceImpl selectService;
    @Test
    public void test_student_selectByTeacherId() {
        List<Student> students = selectService.listStudent(0);
        log.debug("{}", students.size());
    }
}
