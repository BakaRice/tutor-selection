package com.ricemarch.personnel_management_system.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class RepositoryTest {
    @Autowired
    CourseRepository courseRepo;

    @Test
    public void test_list_course() {
//        courseRepo.list(1,)
//                .forEach(u -> log.debug(u.getName() + "\\" + u.getCredit()));
        courseRepo.list(1, PageRequest.of(0, 10)).toString();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void test_update_course() {
        log.debug("{}", courseRepo.update(1, 2.3, "update测试"));
        log.debug("{}",courseRepo.update(2, 60, 5));
    }
}
