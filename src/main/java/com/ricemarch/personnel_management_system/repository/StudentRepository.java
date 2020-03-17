package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
}
