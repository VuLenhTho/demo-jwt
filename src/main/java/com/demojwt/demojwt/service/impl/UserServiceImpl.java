package com.demojwt.demojwt.service.impl;

import com.demojwt.demojwt.dto.UserDTO;
import com.demojwt.demojwt.entity.Role;
import com.demojwt.demojwt.entity.User;
import com.demojwt.demojwt.repository.RoleRepository;
import com.demojwt.demojwt.repository.UserRepository;
import com.demojwt.demojwt.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder encoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void insert(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        Set<Role> roles = new HashSet<>();

        for (String roleSt: userDTO.getRoles()) {
            Role role = roleRepository.findByName(roleSt);
            roles.add(role);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}
