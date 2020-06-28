package com.ricemarch.personnel_management_system.service.impl;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Elective;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.CourseRepository;
import com.ricemarch.personnel_management_system.repository.ElectiveRepository;
import com.ricemarch.personnel_management_system.repository.StudentRepository;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    ElectiveRepository electiveRepository;
    @Autowired
    CourseRepository courseRepository;

    @Override
    @Transactional
    public Teacher addTeacher(int teacher_number, int student_id) {
        /* process of add teacher
         *   1.check whether the number of teachers is sufficient?
         *   2.check whether the requirements are met?
         *       2.1 check if you have taken this teacher's course?
         *       2.2 check if the course passes?
         */
        Teacher teacher = teacherRepository
                .findByNumber(teacher_number)
                .orElseThrow(() -> new CustomException("Failed to add Teacher, the id has been deleted or does not exist:" + teacher_number));
        //1.check whether the number of teachers is sufficient?
        if (teacher.getRanges() <= teacher.getOptional_num()) return null;

        //2.check whether the requirements are met?
        List<Course> teacherCourseList = courseRepository.listByNumber(teacher_number);
        List<Elective> studentCourseList = electiveRepository.list(student_id);
        long count = 0;
        count = studentCourseList.stream()
                .filter(e -> teacherCourseList.contains(e.getCourse()) && e.getGrade() >= e.getCourse().getLowsetSorce())
                .count();

        Student student = studentRepository.find(student_id);
        if (count > 0 && student.getTeacher() == null) {
            student.setTeacher(teacher);
            Integer optional_num = teacher.getOptional_num();
            log.debug("{}", optional_num);
            teacherRepository.addStudentNum(teacher.getId());
            studentRepository.save(student);
        } else throw new CustomException("不符合要求,请及时选择其他导师");
        return teacher;
    }

    @Override
    public Teacher removeTeacher() {
        return null;
    }

    @Override
    public Student get(int student_number) {
        return studentRepository.findbyNumber(student_number);
    }

    @Override
    public List<Teacher> listTeacher() {
        List<Teacher> teachers = teacherRepository.list();
        return teachers;
    }

    @Override
    public Teacher getTeacher(Integer uid) {
        Student student = studentRepository.findById(uid).orElseThrow();
        Teacher teacher = student.getTeacher();
        return teacher;
    }
}
