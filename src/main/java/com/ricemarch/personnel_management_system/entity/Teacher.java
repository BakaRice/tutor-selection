package com.ricemarch.personnel_management_system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int optional_num;//设置实际指导人数

    @OneToMany(mappedBy = "teacher")
    private List<Student> students;

    public Teacher(String name,int optional_num) {
        this.name = name;
        this.optional_num = optional_num;
    }
}
