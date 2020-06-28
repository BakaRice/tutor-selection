package com.ricemarch.personnel_management_system.entity;


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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 0, max = 10, message = "用户名长度必须在{min}到{max}之间")
    @NotNull
    private String name;

    @Max(value = 25, message = "最低分数不能超过{value}")
    @Min(value = 0, message = "学分不能低于{value}")
    @NotNull
    private double credit;

    @Max(value = 100, message = "最低分数不能超过{value}")
    @PositiveOrZero
    @NotNull
    private double lowsetSorce;

    @Max(value = 1, message = "权重不能大于{value}")
    @Min(value = 0, message = "权重不能小于{value}")
    @NotNull
    private Float weight;

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course",cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<Elective> electives;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Teacher teacher;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

    public Course(String name, double credit) {
        this.name = name;
        this.credit = credit;
    }
}
