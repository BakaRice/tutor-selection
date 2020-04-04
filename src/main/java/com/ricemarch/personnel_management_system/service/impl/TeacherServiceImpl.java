package com.ricemarch.personnel_management_system.service.impl;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.entity.User;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.CourseRepository;
import com.ricemarch.personnel_management_system.repository.StudentRepository;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.repository.UserRepository;
import com.ricemarch.personnel_management_system.service.ITeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Course add(Course course, int teacher_id) {
        if (course.getLowsetSorce() == 0) course.setLowsetSorce(60);
        //service层异常到controller层进行处理
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to add course, could not find the specified teacher id:" + teacher_id));
        course.setTeacher(teacher);
        Course save = courseRepository.save(course);
        return save;
    }

    @Override
    @Transactional
    public void remove(int course_id) {
        try {
            courseRepository.deleteById(course_id);
        } catch (Exception e) {
            throw new CustomException("Failed to delete Course," + e.getMessage());
        }
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

    @Override
    public Teacher update(int teacher_id, int range, int optional_num) {
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to update teacher, could not find the specified teacher id:" + teacher_id));
        teacher.setRanges(range);
        teacher.setOptional_num(optional_num);
        teacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public Teacher update(int teacher_id, String name, String introduction) {
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to update teacher, could not find the specified teacher id:" + teacher_id));
        User user = userRepository.find(teacher_id);
        user.setName(name);
//        teacher.setName(name);
        teacher.setIntroduction(introduction);
        teacherRepository.save(teacher);
        userRepository.save(user);
        return teacher;
    }

    @Override
    public List<Student> listStudent(int teacher_id) {
        List<Student> studentsByTeacherId = studentRepository.findStudentsByTeacherId(teacher_id);
        return studentsByTeacherId;
    }

    @Override
    @Transactional
    public Student add(int student_id, int teacher_id) {
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to add student, could not find the specified teacher id:" + teacher_id));
        Student student = studentRepository.findById(student_id)
                .orElseThrow(() -> new CustomException("Failed to add student, could not find the specified student id:" + student_id));
        student.setTeacher(teacher);
        return student;
    }
}
