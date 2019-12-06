package com.demojwt.demojwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;

    private String userName;

    private String password;

    private String email;

    private String name;

    private boolean isEnable;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private Set<String> roles = new HashSet<>();
}
