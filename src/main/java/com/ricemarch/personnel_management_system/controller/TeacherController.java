package com.ricemarch.personnel_management_system.controller;

import com.ricemarch.personnel_management_system.component.RequestComponent;
import com.ricemarch.personnel_management_system.controller.VO.StudentVO;
import com.ricemarch.personnel_management_system.entity.*;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.service.UserService;
import com.ricemarch.personnel_management_system.service.impl.StudentServiceImpl;
import com.ricemarch.personnel_management_system.service.impl.TeacherServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/teachers/")
public class TeacherController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RequestComponent requestComponent;

    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    UserService userService;
//    @GetMapping("welcome")
//    public void getIndex(HttpServletRequest request, @RequestAttribute(MyToken.UID) int uid) {
//        log.debug("{}", (int) request.getAttribute(MyToken.UID));
//        log.debug("{}", uid);
//        log.debug("{}", requestComponent.getUid());
//    }


    @ApiOperation("查询当前登录老师的所有课程")
    @GetMapping("/courses")
//    @Cacheable(cacheNames = "courseList", key = "#requestComponent.uid")
    public Map listCourseByTeacherId() {
        int teahcer_id = requestComponent.getUid();
        List<Course> courseList = teacherService.list(teahcer_id);
        return Map.of("size", courseList.size(), "data", courseList);
    }

    @ApiOperation("查询指定course_id的课程")
    @GetMapping("courses/{course_id}")
    public Map getCourseByCourseId(@ApiParam("课程id") @PathVariable Integer course_id) {
        Course course = teacherService.get(course_id);
        return Map.of("message", "success", "data", course);
    }

    /**
     * RequestBody中的course的id属性 与数据库内操作内容无关 数据库默认自增长
     *
     * @param course
     * @return
     */
    @ApiOperation("为当前登录教师老师添加课程")
    @PostMapping("course")
//    @CachePut(cacheNames = "courseList", key = "123")
//    @CacheEvict(cacheNames = "courseList", key = "#requestComponent.uid")
    public Map addCourse(@Valid @RequestBody Course course) {
//        course.setTeacher(new Teacher(requestComponent.getUid()));
//        if (course.getLowsetSorce() == 0) course.setLowsetSorce(60);
        Course add = teacherService.add(course, requestComponent.getUid());
        return Map.of("message", "success", "data", add);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("courses/{course_id}")
    @Transactional
    @CacheEvict(cacheNames = "course", key = "#course_id")
    public Map deleteCourse(@PathVariable Integer course_id) {
        //删除原有的所有的选课信息
        int romveLine = teacherService.removeCourseStudents(course_id);
        teacherService.remove(course_id);
        return Map.of("message", "success");
    }

    @ApiOperation("修改指定id课程最低分和权重")
    @PatchMapping("courses/setting")
    @ApiImplicitParam(
            name = "course",
            value = "待修改的课程的id、lowestSorce、weight,例:{ \"id\": 1, \"lowsetSorce\": 0, \"weight\": 0 }",
            required = true)
    public Map updateCourse(@Valid @RequestBody Course course) {

        try {
            Integer id = course.getId();
            double lowsetSorce = course.getLowsetSorce();
            float weight = course.getWeight();
            if (id == 0 || lowsetSorce == 0 || weight == 0)
                throw new CustomException("Incoming attribute is incomplete");
            int update = teacherService.update(id, lowsetSorce, weight);
            return Map.of("message", "success", "data", Map.of("updateLine", update));
        } catch (NullPointerException e) {
            throw new CustomException("Incoming attribute is incomplete");
        }
    }

    @ApiOperation("修改指定id课程名称和学分")
    @PatchMapping("courses/info")
    @ApiImplicitParam(
            name = "course",
            value = "待修改的课程的id、lowestSorce、weight,例:{ \"id\": 1, \"lowsetSorce\": 0, \"weight\": 0 }",
            required = true)
    public Map updateCourse2(@Valid @RequestBody Course course) {
        try {
            int cid = course.getId();
            String name = course.getName();
            double credit = course.getCredit();
            if (cid == 0 || name == null || credit == 0) {//不优雅的判空
                throw new CustomException("Incoming attribute is incomplete");
            }
            int update = teacherService.update(cid, name, credit);
            return Map.of("message", "success", "data", Map.of("updateLine", update));
        } catch (NullPointerException e) {
            throw new CustomException("Incoming attribute is incomplete");
        }


    }

    @ApiOperation("为指定id课程添加学生，会覆盖原有该学生选修此课程的信息")
    @PostMapping("courses/{course_id}/students")
    @Transactional
    public void addStudents(@RequestBody List<StudentVO> students, @PathVariable Integer course_id) {
        //删除原有的所有的选课信息
        int romveLine = teacherService.removeCourseStudents(course_id);
        students.forEach(u -> {
            Integer stu_num = u.getNumber();
            User user = userService.getUser(stu_num);
            Student student = new Student();
            //如果不存在则创建 存在user 直接get其student
            if (user == null) {
                user = new User();
                user.setName(u.getName());
                user.setNumber(u.getNumber());
                user.setRole(User.Role.STUDENT);
                user.setPassword(encoder.encode(String.valueOf(stu_num)));
                student.setUser(user);
                userService.addStudent(user, student);
            } else
                student = studentService.get(stu_num);
            teacherService.addStudent(course_id, student, u.getGrade());
        });
    }

    @ApiOperation("为当前登录教师老师添加内定学生")
    @ApiImplicitParam(name = "student", value = "待添加的内定学生", required = true, dataType = "Student")
    @PatchMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        Student add = teacherService.addStudent(student, requestComponent.getUid());
        return add;
    }

    @ApiOperation("列出当前登录老师的所有学生")
    @GetMapping("/students")
    public Map findAllStudentByTeacherID() {
        Integer teacher_id = requestComponent.getUid();
        List<Student> studentList = teacherService.listStudent(teacher_id);
        return Map.of("size", studentList.size(), "data", studentList);
    }


    @ApiOperation("列出当前登录老师的个人信息")
    @GetMapping("/teacher")
    public Teacher listTeacherInfo() {
        int teacher_uid = requestComponent.getUid();
        Teacher teacher = teacherService.getTeacher(teacher_uid);
        return teacher;
    }

    @ApiOperation("修改老师信息 只能修改ranges name introduction")
    @PostMapping("/teahcer")
    public void updateTeacherInfo(@Valid @RequestBody Teacher update) {

        int teacher_uid = requestComponent.getUid();
        int ranges = update.getRanges();
        String introduction = update.getIntroduction();
        String name = update.getUser().getName();
        teacherService.update(teacher_uid, ranges);
        teacherService.update(teacher_uid, name, introduction);
    }


}
