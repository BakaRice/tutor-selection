package com.ricemarch.personnel_management_system.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class RepositoryTest {
    @Autowired
    CourseRepository courseRepo;

    @Test
    public void test_list_course() {
        courseRepo.list(1)
                .forEach(u -> log.debug(u.getName() + "\\" + u.getCredit()));
    }
}
