package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;

import java.util.List;

public interface ICourseService {

    public Course add(Course course,int teacher_id);//添加课程信息，无法找到teacher_id时会抛出异常

    public int remove(int course_id);//删除某个课程

    public int update(int course_id, double LowestScore, int weight);//修改最低分要求和权重

    public int update(int course_id, String name, double credit);//修改课程名和学分

    public List<Course> list(int teacher_id);//列出教师所有课程

    public Course get(int course_id);

}
