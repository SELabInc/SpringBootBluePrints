package com.selab.springbootblueprints.model.bean;

import com.selab.springbootblueprints.model.entity.User;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class UserVO {
    private long id;
    private String username;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private String groupName;
    private ZonedDateTime registerDate;

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.enabled = user.getEnabled();
        this.accountNonExpired = user.getAccountNonExpired();
        this.accountNonLocked = user.getAccountNonLocked();
        this.credentialsNonExpired = user.getCredentialsNonExpired();
        this.groupName = user.getUserGroup().getName();
        this.registerDate = user.getRegisterDate();
    }
}
