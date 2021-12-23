package com.selab.springbootblueprints.model.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDTO {
    @NotBlank
    private String groupName;

    @Size(max= 21)
    private String password;
}
