package com.ricemarch.personnel_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"electives", "teacher"})
public class Student {

    @Id
    private Integer id;

    @OneToOne//可以用级联
    @MapsId
    private User user;

//    private String name;


    @OneToMany(mappedBy = "student", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Elective> electives;

    @ManyToOne
    private Teacher teacher;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

    public Student(int student_number, String name) {
        this.id = student_number;
//        this.name = name;
    }
}
