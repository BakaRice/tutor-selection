package com.ricemarch.personnel_management_system.service.impl;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.CourseRepository;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.service.ICourseService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CourseServiceImpl implements ICourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    @Transactional
    public Course add(Course course, int teacher_id) {
        if (course.getLowsetSorce() == 0) course.setLowsetSorce(60);
        Teacher teacher = null;
        //service层异常到controller层进行处理
        teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Can not find teacher id:" + teacher_id + ",Course add failed."));
        course.setTeacher(teacher);
        Course save = courseRepository.save(course);
        return save;
    }

    @Override
    @Transactional
    public int remove(int course_id) {
        int removeLine = courseRepository.remove(course_id);
        return removeLine;
    }

    @Override
    @Transactional
    public int update(int course_id, double LowestScore, int weight) {
        return courseRepository.update(course_id, LowestScore, weight);
    }

    @Override
    @Transactional
    public int update(int course_id, String name, double credit) {
        return courseRepository.update(course_id, credit, name);
    }

    @Override
    public List<Course> list(int teacher_id) {
        List<Course> courseList = courseRepository.list(teacher_id);
        return courseList;
    }

    @Override
    public Course get(int course_id) {
        return courseRepository.findById(course_id).orElse(null);
    }

}
