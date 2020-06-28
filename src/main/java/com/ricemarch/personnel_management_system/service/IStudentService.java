package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;

import java.util.List;

public interface IStudentService {
    //学生操作services
    public Teacher addTeacher(int teacher_id,int student_id);//选择导师

    public Teacher removeTeacher();//取消选择

    public Student get(int student_number);//获取学生

    public List<Teacher> listTeacher();//列出所有老师

    Teacher getTeacher(Integer uid);//查询自己的老师
}
