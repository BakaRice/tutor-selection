package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Elective;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITeacherService {

    /**
     * 教师 人数信息修改
     *
     * @param teacher_id 教师id
     * @param ranges 教师最大指导数量
     * @return Teacher 教师类（被修改后的对象）
     */
    public Teacher update(int teacher_id, int ranges);

    /**
     * 教师 自身信息修改
     *
     * @param teacher_id 教师id
     * @param name 姓名
     * @param introduction 教师自我介绍
     * @return Teacher Teacher 教师类（被修改后的对象）
     */
    public Teacher update(int teacher_id, String name, String introduction);

    /**
     * 列出所有被选学生
     *
     * @return
     */
    public List<Student> listStudent(int teacher_id);

    /**
     * 添加内定学生
     *
     * @param student 学生类
     * @return
     */
    public Student addStudent(Student student, int teacher_id);

    /**
     * 添加课程信息，无法找到teacher_id时会抛出异常
     *
     * @param course
     * @param teacher_id
     * @return
     */
    public Course add(Course course, int teacher_id);

    /**
     * 删除某个课程
     *
     * @param course_id
     * @return
     */
    public void remove(int course_id);

    /**
     * 修改指定id课程的最低分要求和权重
     *
     * @param course_id
     * @param LowestScore
     * @param weight
     * @return
     */
    public int update(int course_id, double LowestScore, float weight);

    /**
     * 修改指定id课程的修改课程名和学分
     *
     * @param course_id
     * @param name
     * @param credit
     * @return
     */
    public int update(int course_id, String name, double credit);

    /**
     * 为指定id课程添加学生
     * @param course_id 课程id
     * @param student 学生对象
     * @return Elective
     */
    public Elective addStudent(int course_id, Student student, float grade);

    /**
     * 列出指定id教师所有课程
     *
     * @param teacher_id
     * @return
     */
    public List<Course> list(int teacher_id);

    public Course get(int course_id);

    /**
     * 获取指定id教师的信息
     * @param teahcer_id
     * @return
     */
    public Teacher getTeacher(int teahcer_id);

    /**
     * 删除指定id课程选课表的所有学生
     * @param course_id
     * @return
     */
    int removeCourseStudents(Integer course_id);
}
