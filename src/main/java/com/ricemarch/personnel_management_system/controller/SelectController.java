package com.ricemarch.personnel_management_system.controller;

import com.ricemarch.personnel_management_system.entity.Student;
import com.ricemarch.personnel_management_system.service.impl.SelectServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/select/")
public class SelectController {

    @Autowired
    SelectServiceImpl selectService;


    @ApiOperation("为指定ID的老师添加指定id的内定学生")
    @PatchMapping("students/{student_id}/teacher/{teacher_id}")
    public Student addStudent(@PathVariable int student_id, @PathVariable int teacher_id) {
        Student add = selectService.add(student_id, teacher_id);
        return add;
    }

    @ApiOperation("列出指定id老师的所有学生")
    @GetMapping("students/teacher/{teacher_id}")
    public Map findAllStudentByTeacherID(@PathVariable int teacher_id) {
        List<Student> studentList = selectService.listStudent(teacher_id);
        return Map.of("size", studentList.size(), "data", studentList);
    }

}
