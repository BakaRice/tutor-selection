package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends BaseRepository<Course, Integer> {

    //查出指定老师id 的所有课程 要用 IN 不能用 =
    @Query("select c from Course c where c.teacher.id=:tid")
    List<Course> list(@Param("tid") int tid);


}
