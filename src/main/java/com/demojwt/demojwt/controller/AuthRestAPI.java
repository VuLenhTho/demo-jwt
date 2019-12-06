package com.demojwt.demojwt.controller;

import com.demojwt.demojwt.dto.UserDTO;
import com.demojwt.demojwt.entity.User;
import com.demojwt.demojwt.jwt.JwtProvider;
import com.demojwt.demojwt.jwt.JwtResponse;
import com.demojwt.demojwt.repository.UserRepository;
import com.demojwt.demojwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPI {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder encoder;
    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public AuthRestAPI(AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtProvider jwtProvider, UserRepository userRepository, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDTO userDTO) {

        User user = userRepository.findByUserName(userDTO.getUserName()).orElseThrow(() -> new UsernameNotFoundException("Not found user with username : " + userDTO.getUserName()));

        if(encoder.matches(userDTO.getPassword(),user.getPassword())){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUserName(),
                            userDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }else {
            return ResponseEntity.ok().body("Incorrect Password");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        if(userRepository.existsByUserName(userDTO.getUserName())) {
            return new ResponseEntity<>("Fail -> Username is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            return new ResponseEntity<>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        userService.insert(userDTO);
        return ResponseEntity.ok().body("User registered successfully!");
    }
}
