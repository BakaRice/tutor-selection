package com.ricemarch.personnel_management_system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Elective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String grade;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
}
