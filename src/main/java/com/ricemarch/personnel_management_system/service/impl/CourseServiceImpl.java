package com.ricemarch.personnel_management_system.service.impl;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.CourseRepository;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.service.ICourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
                .orElseThrow(() -> new CustomException("Failed to add course, could not find the specified teacher id:" + teacher_id));
        course.setTeacher(teacher);
        Course save = courseRepository.save(course);
        return save;
    }

    @Override
    @Transactional
    public int remove(int course_id) {
        int removeLine = courseRepository.remove(course_id);
        if (removeLine == 0)
            throw new CustomException("Failed to delete Course, the id has been deleted or does not exist:" + course_id);
        return removeLine;
    }

    @Override
    @Transactional
    public int update(int course_id, double LowestScore, int weight) {
        int update = courseRepository.update(course_id, LowestScore, weight);
        if (update == 0)
            throw new CustomException("Failed to update Course, the id has been deleted or does not exist:" + course_id);
        return update;
    }

    @Override
    @Transactional
    public int update(int course_id, String name, double credit) {
        int update = courseRepository.update(course_id, credit, name);
        if (update == 0)
            throw new CustomException("Failed to update Course, the id has been deleted or does not exist:" + course_id);
        return update;
    }

    @Override
    public Page<Course> list(int teacher_id, Pageable pageable) {
        Page<Course> courseList = courseRepository.list(teacher_id, pageable);
        return courseList;
    }

    @Override
    public Course get(int course_id) {
        return courseRepository.findById(course_id)
                .orElseThrow(() -> new CustomException("Failed to get course, could not find the specified course id:" + course_id));
    }

}
