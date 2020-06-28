package com.ricemarch.personnel_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"courses", "students"})
public class Teacher {

    @Id
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @MapsId
    private User user;

    @Size(min = 0, max = 50, message = "About Me must be between 0 and 200 characters")
    @NotNull
    private String introduction;

    private Integer optional_num;//设置实际指导人数

    @Max(value = 129,message = "最大指导人数不能超过{value}")
    @PositiveOrZero
    private Integer ranges;//设置可以被选的范围

    @OneToMany(mappedBy = "teacher")
    @ApiModelProperty(hidden = true)
    private List<Course> courses;

    @OneToMany(mappedBy = "teacher")
    @ApiModelProperty(hidden = true)
    private List<Student> students;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    public Teacher(String name, int optional_num) {
        this.optional_num = optional_num;
    }

}
