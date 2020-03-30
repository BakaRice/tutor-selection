package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {

    public List<Student> findStudentsByTeacherId(int teacher_id);
}
