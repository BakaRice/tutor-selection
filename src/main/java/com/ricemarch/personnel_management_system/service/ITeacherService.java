package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;

import java.util.List;

public interface ITeacherService {
    //教师操作services
    /*
    查询自己教授的课程，查询自己的学生。
     */
    public List<Student> listStudent();

    public List<Course> listCourse();


}
