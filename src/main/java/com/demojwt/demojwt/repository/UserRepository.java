package com.demojwt.demojwt.repository;

import com.demojwt.demojwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
}
