package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends BaseRepository<Course, Integer> {

    //查出指定老师id 的所有课程 要用 IN 不能用 =
    @Query("select c from Course c where c.teacher.id=:tid")
    Page<Course> list(@Param("tid") int tid, Pageable pageable);

    @Modifying
    @Query("delete from Course  c where  c.id=:course_id")
    public int remove(@Param("course_id") int course_id);

    @Modifying
    @Query("UPDATE Course  c set c.credit=:credit,c.name=:cname where c.id=:cid")
    public int update(
            @Param("cid") int cid,
            @Param("credit") double credit,
            @Param("cname") String name
    );

    @Modifying
    @Query("UPDATE Course  c set c.lowsetSorce=:lowestScore,c.weight=:weight where c.id=:cid")
    public int update(
            @Param("cid") int cid,
            @Param("lowestScore") double lowestScore,
            @Param("weight") int weight
    );

}
