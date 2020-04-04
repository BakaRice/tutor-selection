package com.ricemarch.personnel_management_system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @MapsId
    private User user;

    private String introduction;

    private Integer optional_num;//设置实际指导人数

    private Integer ranges;//设置可以被选的范围

    @OneToMany(mappedBy = "teacher")
    private List<Student> students;


    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    public Teacher(String name, int optional_num) {
        this.optional_num = optional_num;
    }

//    public Teacher(int uid) {
//        this.id = uid;
//    }
}
