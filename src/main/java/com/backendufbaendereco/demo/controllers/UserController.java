package com.backendufbaendereco.demo.controllers;


import com.backendufbaendereco.demo.DTO.UserCreateRequest;
import com.backendufbaendereco.demo.DTO.UserFindResponse;
import com.backendufbaendereco.demo.DTO.UserResponse;
import com.backendufbaendereco.demo.entities.user.User;
import com.backendufbaendereco.demo.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserCreateRequest userRequest) throws MessagingException, UnsupportedEncodingException {
       User user = userRequest.toModel();
        UserResponse savedUser = userService.registerUser(user);
        return ResponseEntity.ok().body(savedUser);
    }

    @GetMapping("/verify")
    public String verify(@Param("code") String code){
       return userService.verify(code) ? "verify_success" : "verify_fail" ;
    }
    @GetMapping()
    public List<UserFindResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping("{id}")
    public UserFindResponse findById(@PathVariable("id") Long id){
        return userService.findById(id);
    }



}
