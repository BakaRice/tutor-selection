package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.service.impl.CourseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class CourseServiceTest {
    @Autowired
    CourseServiceImpl courseService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void test_update_course() {
        log.debug("{}", courseService.update(1, "service测试", 2));
        log.debug("{}", courseService.update(1, 66, 12));
    }

    @Test
    public void test_query_course() {
//        log.debug("{}", courseService.list(1).get(1).getName());
//        log.debug("{}", courseService.get(1).getName());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void test_delete_course() {
        log.debug("{}", courseService.remove(5));
    }

    @Test
    @Transactional
//    @Rollback(value = false)
    public void error_test_add_course() {
        Course course = new Course("ServiceTest6", 2);
        Assertions.assertThrows(CustomException.class, () -> courseService.add(course, 3));
    }

    @Test
    public void test_list_course() {
//        for (Course course : courseService.list(1)) {
//            log.debug("{}", course.getName());
//        }
        courseService.list(1, PageRequest.of(0, 10)).toString();
    }

    @Test
    public void test_get_course() {
        log.debug("{}", courseService.get(1).getName());
    }

}
