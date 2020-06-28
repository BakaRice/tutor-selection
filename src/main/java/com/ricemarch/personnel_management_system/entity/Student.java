package com.ricemarch.personnel_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"teacher", "electives"})
public class Student {

    @Id
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    private User user;

    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "student", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Elective> electives;

    @ManyToOne
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

}
