package com.ricemarch.personnel_management_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class User {

    public enum Role {
        STUDENT, TEACHER
    }

    @Id
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private Integer number;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//只能进行反序列化 json->java object
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(hidden = true)
    private Role role;

    @ApiModelProperty(hidden = true)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

}
