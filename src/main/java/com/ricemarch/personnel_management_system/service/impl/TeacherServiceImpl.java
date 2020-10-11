package com.ricemarch.personnel_management_system.service.impl;

import com.ricemarch.personnel_management_system.entity.*;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.*;
import com.ricemarch.personnel_management_system.service.ITeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    ElectiveRepository electiveRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    @CachePut(cacheNames = "course", key = "#result.id")
    public Course add(Course course, int teacher_id) {
        //service层异常到controller层进行处理
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to add course, could not find the specified teacher id:" + teacher_id));
        course.setTeacher(teacher);
        Course save = courseRepository.save(course);
        return save;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "courseList", key = "#teacher_id")
    public void remove(int course_id) {
        try {
            courseRepository.deleteById(course_id);
        } catch (Exception e) {
            throw new CustomException("Failed to delete Course," + e.getMessage());
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "course", key = "#course_id")
    public int update(int course_id, double LowestScore, float weight) {
        int update = courseRepository.update(course_id, LowestScore, weight);
        if (update == 0)
            throw new CustomException("Failed to update Course, the id has been deleted or does not exist:" + course_id);
        return update;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "course", key = "#course_id")
    public int update(int course_id, String name, double credit) {
        int update = courseRepository.update(course_id, credit, name);
        if (update == 0)
            throw new CustomException("Failed to update Course, the id has been deleted or does not exist:" + course_id);
        return update;
    }

    @Override
    public Elective addStudent(int course_id, Student student, float grade) {
        Course course = courseRepository.findById(course_id).orElseThrow(() -> new CustomException("Failed to get course, could not find the specified course id:" + course_id));
        Elective elective = new Elective();
        //如果原来有成绩则会删除原有成绩信息
//        electiveRepository.remove(course_id, student.getId());
        elective.setCourse(course);
        elective.setStudent(student);
        elective.setGrade(grade);
        Elective save = electiveRepository.save(elective);
        return save;
    }

    @Override
    public List<Course> list(int teacher_id) {
        List<Course> courseList = courseRepository.list(teacher_id);
        return courseList;
    }

    @Override
    @Cacheable(cacheNames = "course", key = "#course_id")
    public Course get(int course_id) {
        return courseRepository.findById(course_id)
                .orElseThrow(() -> new CustomException("Failed to get course, could not find the specified course id:" + course_id));
    }

    @Override
    @CachePut(cacheNames = "teahcer", key = "#teacher_id")
    public Teacher update(int teacher_id, int ranges) {
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to update teacher, could not find the specified teacher id:" + teacher_id));
        teacher.setRanges(ranges);
//        teacher.setOptional_num(optional_num);
        teacherRepository.save(teacher);
        return teacher;
    }

    @Override
    @CachePut(cacheNames = "teahcer", key = "#teacher_id")
    public Teacher update(int teacher_id, String name, String introduction) {
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to update teacher, could not find the specified teacher id:" + teacher_id));
        teacher.getUser().setName(name);
        teacher.setIntroduction(introduction);
        teacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public List<Student> listStudent(int teacher_id) {
        List<Student> studentsByTeacherId = new ArrayList<>();
        studentsByTeacherId = studentRepository.findStudentsByTeacherId(teacher_id);
        return studentsByTeacherId;
    }

    /**
     * 添加内定学生，
     * 如果该学生不存在 则会直接创建，
     * 如果该学生已经做出选择，则会抛出CustomException异常
     *
     * @param s          学生对象
     * @param teacher_id 教师id
     * @return 添加的学生对象
     */
    @Override
    @Transactional
    public Student addStudent(Student s, int teacher_id) {
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new CustomException("Failed to add student, could not find the specified teacher id:" + teacher_id));
        Student student = Optional.ofNullable(studentRepository.findbyNumber(/*s.getUser().getName(),*/ s.getUser().getNumber()))
                .orElseGet(() -> {
                    User u = s.getUser();
                    u.setPassword(encoder.encode(String.valueOf(s.getUser().getNumber())));
                    u.setRole(User.Role.STUDENT);
                    userRepository.save(u);
                    return s;
                });
        if (student.getTeacher() != null)
            throw new CustomException("Failed to add student,The student has made a choice,student id is" + teacher_id);
        student.setTeacher(teacher);
        studentRepository.save(student);
        teacherRepository.addStudentNum(teacher_id);
        return student;
    }

    @Override
    @Cacheable(cacheNames = "teahcer", key = "#teacher_id")
    public Teacher getTeacher(int teacher_id) {
        Teacher teacher = teacherRepository.find(teacher_id);
        if (teacher == null)
            throw new CustomException("Failed to find teacher information, could not find the specified teacher id:" + teacher_id);
        else return teacher;
    }

    @Override
    public int removeCourseStudents(Integer course_id) {
        int remove = electiveRepository.remove(course_id);
        return remove;
    }

}
