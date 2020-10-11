package com.ricemarch.personnel_management_system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Elective implements Serializable {
    @Transient
    private static final long serialVersionUID = -8894316047560440770L;
    //学生对应选课表
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float grade;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp ",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
}
