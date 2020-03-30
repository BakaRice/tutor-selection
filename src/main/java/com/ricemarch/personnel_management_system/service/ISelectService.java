package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;

import java.util.List;

public interface ISelectService {
    //学生操作services
    public Teacher addTeacher();//选择导师

    public Teacher removeTeacher();//取消选择


    //老师操作services

    /**
     * 列出所有被选学生
     * @return
     */
    public List<Student> listStudent(int teacher_id);

    /**
     * 添加内定学生
     * @param student_id
     * @return
     */
    public Student add(int student_id,int teacher_id);
}
