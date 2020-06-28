package com.ricemarch.personnel_management_system.repository;

import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {

    @Query("select s from Student s where s.teacher.id = :teacher_id")
    List<Student> findStudentsByTeacherId(@Param("teacher_id") int teacher_id);

    @Query("select s.id from  Student s")
    List<Integer> list();

    @Query("select s from Student s where s.teacher.id = :teacher_id")
    List<Student> list(@Param("teacher_id") int teacher_id);

    @Query("from Student s where s.user.name=:name and s.user.number=:num")
    Student find(@Param("name") String name, @Param("num") int num);

    @Query("from Student s where s.id=:uid")
    Student find(@Param("uid") int uid);

    @Query("from Student s where s.user.number=:num")
    Student findbyNumber(@Param("num") int num);

    /**
     * 通过老师的id查询学生
     * @param uid
     * @return
     */
    @Query("from Student s where s.teacher.id=:uid")
    List<Student> find(@Param("uid") Integer uid);

    @Query("select  s.user from Student s where s.teacher.id=:uid")
    List<User> find_user(@Param("uid") Integer uid);
}
