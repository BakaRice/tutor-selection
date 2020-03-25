package com.ricemarch.personnel_management_system.controller;

import com.ricemarch.personnel_management_system.entity.Course;
import com.ricemarch.personnel_management_system.service.impl.CourseServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/courses/")
public class CourseController {

    @Autowired
    CourseServiceImpl courseService;

    @ApiOperation("查询指定teacher_id老师的所有课程")
    @GetMapping("teacher/{teacher_id}")
    public Page listCourseByTeacherId(@PathVariable int teacher_id, @PageableDefault(value = 15, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Course> coursePage = courseService.list(teacher_id, pageable);
        return coursePage;
    }

    @ApiOperation("查询指定course_id的课程")
    @GetMapping("{course_id}")
    public Map getCourseByCourseId(@ApiParam("课程id") @PathVariable int course_id) {
        Course course = courseService.get(course_id);
        return Map.of("message", "success", "data", course);
    }

    @ApiOperation("添加课程，指定关联教师id")
    @PostMapping()
    public Map addCourse(@RequestBody Course course, @RequestParam int teacher_id) {
        Course add = courseService.add(course, teacher_id);
        return Map.of("message", "success", "data", add);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("{course_id}")
    public Map deleteCourse(@PathVariable int course_id) {
        int remove = courseService.remove(course_id);
        return Map.of("message", "success", "data", Map.of("deleteLine", remove));
    }

    @ApiOperation("修改指定id课程最低分和权重")
    @PatchMapping("{course_id}/LowestScore/{LowestScore}/weight/{weight}")
    public Map updateCourse(@PathVariable int course_id, @PathVariable double LowestScore, @PathVariable int weight) {
        int update = courseService.update(course_id, LowestScore, weight);
        return Map.of("message", "success", "data", Map.of("updateLine", update));
    }

    @ApiOperation("修改指定id课程名称和学分")
    @PatchMapping("{course_id}/name/{name}/credit/{credit}")
    public Map updateCourse2(@PathVariable int course_id, @PathVariable String name, @PathVariable double credit) {
        int update = courseService.update(course_id, name, credit);
        return Map.of("message", "success", "data", Map.of("updateLine", update));
    }

}
