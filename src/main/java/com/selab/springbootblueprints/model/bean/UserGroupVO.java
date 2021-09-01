package com.selab.springbootblueprints.model.bean;

import com.selab.springbootblueprints.model.entity.UserGroup;
import lombok.Value;

@Value
public class UserGroupVO {
    private long id;
    private String name;

    public UserGroupVO(UserGroup entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
