package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;

import java.util.List;

public interface IStudentService {
    //学生操作services
    public Teacher addTeacher();//选择导师

    public Teacher removeTeacher();//取消选择


}
