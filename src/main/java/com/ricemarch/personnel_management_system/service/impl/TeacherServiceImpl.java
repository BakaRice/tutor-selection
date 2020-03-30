package com.ricemarch.personnel_management_system.service.impl;

import com.ricemarch.personnel_management_system.entity.Teacher;
import com.ricemarch.personnel_management_system.exception.CustomException;
import com.ricemarch.personnel_management_system.repository.TeacherRepository;
import com.ricemarch.personnel_management_system.service.ITeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    TeacherRepository teacherRepository;

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
        teacher.setName(name);
        teacher.setIntroduction(introduction);
        teacherRepository.save(teacher);
        return teacher;
    }
}
