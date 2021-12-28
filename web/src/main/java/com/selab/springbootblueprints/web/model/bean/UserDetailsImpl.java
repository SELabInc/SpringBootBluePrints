package com.selab.springbootblueprints.web.model.bean;

import com.selab.springbootblueprints.web.model.entity.User;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class UserDetailsImpl implements UserDetails {
    private final String username;      // 실제 name 파라미터가 아닌 로그인 시 사용되는 값, 반드시 유니크한 컬럼이어야함
    private final String password;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private Set<GrantedAuthority> authorities;

    public UserDetailsImpl(User userEntity, Auth... auths) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.enabled = userEntity.getEnabled();
        this.accountNonExpired = userEntity.getAccountNonExpired();
        this.accountNonLocked = userEntity.getAccountNonLocked();
        this.credentialsNonExpired = userEntity.getCredentialsNonExpired();
        this.authorities = Arrays.stream(auths).collect(Collectors.toSet());
    }
}
