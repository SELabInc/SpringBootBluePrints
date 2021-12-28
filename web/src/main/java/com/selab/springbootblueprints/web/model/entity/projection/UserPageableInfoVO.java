package com.selab.springbootblueprints.web.model.entity.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserPageableInfoVO {

    long getId();

    String getUsername();

    boolean isEnabled();

    @Value("#{target.userGroup.name}")
    String getGroupName();

}
