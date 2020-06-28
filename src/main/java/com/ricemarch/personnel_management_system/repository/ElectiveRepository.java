package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Elective;
import com.ricemarch.personnel_management_system.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ElectiveRepository extends BaseRepository<Elective, Integer> {

    @Query("select e from Elective e where e.student.id = :student_id")
    List<Elective> list(@Param("student_id") int student_id);

    @Modifying
    @Query("delete from Elective e where e.course.id = :course_id ")
    int remove(@Param("course_id") Integer course_id);

}
