package com.ricemarch.personnel_management_system.service.impl;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.ElectiveRepository;
import com.ricemarch.personnel_management_system.repository.StudentRepository;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.service.ISelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SelectServiceImpl implements ISelectService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    ElectiveRepository electiveRepository;

    @Override
    public Teacher addTeacher() {
        return null;
    }

    @Override
    public Teacher removeTeacher() {
        return null;
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
