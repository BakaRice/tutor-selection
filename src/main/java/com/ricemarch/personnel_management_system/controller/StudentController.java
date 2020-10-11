package com.ricemarch.personnel_management_system.controller;

import com.ricemarch.personnel_management_system.component.RequestComponent;
import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.service.impl.StudentServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/students/")
public class StudentController {
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private StudentServiceImpl studentService;


    @GetMapping("/teahcers/{tnumber}")
    @ApiOperation("选取导师")
    public Teacher choseTeacher(@PathVariable("tnumber") int tnumber) {
        int uid = requestComponent.getUid();
        Teacher teacher = studentService.addTeacher(tnumber, uid);
        return teacher;
    }

    @ApiOperation("查询所有老师")
    @GetMapping("/teachers")
    public List<Teacher> listTeacher() {
        List<Teacher> teachers = studentService.listTeacher();
        return teachers;
    }

    @ApiOperation("查询自己的老师")
    @GetMapping("/teacher")
//    @Cacheable(cacheNames = "myTeacher", key = "#requestComponent.uid")
    public Teacher getTeahcer() {
        int uid = requestComponent.getUid();
        Teacher teacher = studentService.getTeacher(uid);
        return teacher;
    }
}
