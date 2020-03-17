package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;

import java.util.List;

public interface ICourseService {

    public Course add(Course course);//添加课程信息

    public List<Student> add(int course_id, List<Student> students);//为某一课程添加学生

    public Course remove(int course_id);//删除某个课程

    public Course update(int course_id, double LowestScore, double weight);//修改最低分要求和权重

    public Course update(int course_id, String name, double credit);//修改课程名和学分

    public Course list(int teacher_id);//列出教师所有课程

    public Course get(int course_id);

}
