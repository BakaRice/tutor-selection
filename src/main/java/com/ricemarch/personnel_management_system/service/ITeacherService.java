package com.ricemarch.personnel_management_system.service;

import com.ricemarch.personnel_management_system.entity.Teacher;

public interface ITeacherService {
    /**
     * 教师 人数信息修改
     * @param teacher_id
     * @param range
     * @param optional_num
     * @return
     */
    public Teacher update(int teacher_id, int range, int optional_num);


    /**
     * 教师 自身信息修改
     * @param teacher_id
     * @param name
     * @param introduction
     * @return
     */
    public Teacher update(int teacher_id,String name,String introduction);

}
