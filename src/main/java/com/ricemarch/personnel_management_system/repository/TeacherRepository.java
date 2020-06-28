package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Teacher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher, Integer> {

    @Query("from Teacher t")
    List<Teacher> list();

    @Query("from Teacher t where  t.id=:tid")
    Teacher find(@Param("tid") Integer tid);

    @Query("from Teacher t where  t.user.number=:tnumber")
    Optional<Teacher> findByNumber(@Param("tnumber") Integer tnumber);

    @Modifying
    @Query("update Teacher t set t.optional_num= t.optional_num+1 where t.id = :tid")
    int addStudentNum(@Param("tid") Integer tid);

    @Modifying
    @Query("update Teacher t set t.optional_num= t.optional_num+1 where t.user.number=:tnumber")
    int addStudentNumByTeacherNumber(@Param("tnumber") Integer tnumber);


    @Modifying
    @Query("update Teacher t set t.optional_num= t.optional_num-1 where t.id = :tid")
    int removeStudentNum(@Param("tid") Integer tid);
}
