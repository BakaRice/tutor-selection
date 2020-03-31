package com.ricemarch.personnel_management_system.controller;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.service.impl.TeacherServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/teachers/")
public class TeacherController {

    @Autowired
    TeacherServiceImpl teacherService;

    /**
     * 问题： 没有id校验过程 -1 111 任意数字都可以进入
     * @param teacher_id
     * @param pageable
     * @return
     */
    @ApiOperation("查询指定teacher_id老师的所有课程")
    @GetMapping("{teacher_id}/courses/")
    public Page listCourseByTeacherId(@PathVariable Integer teacher_id, @PageableDefault(value = 15, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Course> coursePage = teacherService.list(teacher_id, pageable);
        return coursePage;
    }

    @ApiOperation("查询指定course_id的课程")
    @GetMapping("courses/{course_id}")
    public Map getCourseByCourseId(@ApiParam("课程id") @PathVariable Integer course_id) {
        Course course = teacherService.get(course_id);
        return Map.of("message", "success", "data", course);
    }

    /**
     * RequestBody中的course的id属性 与数据库内操作内容无关 数据库默认自增长
     * @param course
     * @param teacher_id
     * @return
     */
    @ApiOperation("添加课程，指定关联教师id")
    @PostMapping("courses")
    public Map addCourse(@RequestBody Course course, @RequestParam Integer teacher_id) {
        Course add = teacherService.add(course, teacher_id);
        return Map.of("message", "success", "data", add);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("courses/{course_id}")
    public Map deleteCourse(@PathVariable Integer course_id) {
        teacherService.remove(course_id);
        return Map.of("message", "success");
    }

    @ApiOperation("修改指定id课程最低分和权重")
    @PatchMapping("courses/{course_id}/LowestScore/{LowestScore}/weight/{weight}")
    public Map updateCourse(@PathVariable Integer course_id, @PathVariable double LowestScore, @PathVariable Integer weight) {
        int update = teacherService.update(course_id, LowestScore, weight);
        return Map.of("message", "success", "data", Map.of("updateLine", update));
    }

    @ApiOperation("修改指定id课程名称和学分")
    @PatchMapping("courses/{course_id}/name/{name}/credit/{credit}")
    public Map updateCourse2(@PathVariable Integer course_id, @PathVariable String name, @PathVariable double credit) {
        int update = teacherService.update(course_id, name, credit);
        return Map.of("message", "success", "data", Map.of("updateLine", update));
    }

    @ApiOperation("为指定ID的老师添加指定id的内定学生")
    @PatchMapping("{teacher_id}/students/{student_id}/")
    public Student addStudent(@PathVariable Integer student_id, @PathVariable Integer teacher_id) {
        Student add = teacherService.add(student_id, teacher_id);
        return add;
    }

    @ApiOperation("列出指定id老师的所有学生")
    @GetMapping("{teacher_id}/students/")
    public Map findAllStudentByTeacherID(@PathVariable Integer teacher_id) {
        List<Student> studentList = teacherService.listStudent(teacher_id);
        return Map.of("size", studentList.size(), "data", studentList);
    }

}
