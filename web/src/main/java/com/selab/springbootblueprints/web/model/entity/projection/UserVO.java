package com.selab.springbootblueprints.web.model.entity.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface UserVO {

   long getId();

   String getUsername();

   boolean isEnabled();

   boolean isAccountNonExpired();

   boolean isAccountNonLocked();

   boolean isCredentialsNonExpired();

   @Value("#{target.userGroup.name}")
   String getGroupName();

   LocalDateTime getRegisterDate();
}
