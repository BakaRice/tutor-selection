package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITeacherService {

    /**
     * 教师 人数信息修改
     *
     * @param teacher_id
     * @param range
     * @param optional_num
     * @return
     */
    public Teacher update(int teacher_id, int range, int optional_num);

    /**
     * 教师 自身信息修改
     *
     * @param teacher_id
     * @param name
     * @param introduction
     * @return
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
     * @param student_id
     * @return
     */
    public Student add(int student_id, int teacher_id);

    /**
     * 添加课程信息，无法找到teacher_id时会抛出异常
     * @param course
     * @param teacher_id
     * @return
     */
    public Course add(Course course, int teacher_id);

    /**
     * 删除某个课程
     * @param course_id
     * @return
     */
    public void remove(int course_id);

    /**
     * 修改指定id课程的最低分要求和权重
     * @param course_id
     * @param LowestScore
     * @param weight
     * @return
     */
    public int update(int course_id, double LowestScore, int weight);

    /**
     * 修改指定id课程的修改课程名和学分
     * @param course_id
     * @param name
     * @param credit
     * @return
     */
    public int update(int course_id, String name, double credit);

    /**
     * 列出指定id教师所有课程
     * @param teacher_id
     * @param pageable
     * @return
     */
    public Page<Course> list(int teacher_id, Pageable pageable);

    public Course get(int course_id);
}
