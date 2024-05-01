package com.backendufbaendereco.demo.controllers;


import com.auth0.jwt.interfaces.Claim;
import com.backendufbaendereco.demo.DTO.AddressRequest;
import com.backendufbaendereco.demo.DTO.UserCreateRequest;
import com.backendufbaendereco.demo.DTO.UserFindResponse;
import com.backendufbaendereco.demo.DTO.UserResponse;
import com.backendufbaendereco.demo.entities.user.User;
import com.backendufbaendereco.demo.helper.JwtHelper;
import com.backendufbaendereco.demo.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;


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


    @PostMapping("/{id}/address")

    public ResponseEntity<?>   createUserAddress(@PathVariable("id") Long id,  @RequestBody @Valid AddressRequest address, HttpServletRequest request){

        Map<String, Claim> userData = jwtHelper.getUserDataFromJwtToken(request);
        Long userId = userData.get("userId").asLong();

        if (!id.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O ID fornecido não corresponde ao usuário logado");
        }
        userService.createUserAddress(address,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
