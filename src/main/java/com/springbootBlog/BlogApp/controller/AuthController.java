package com.springbootBlog.BlogApp.controller;

import com.springbootBlog.BlogApp.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/sigin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
       Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Uer signed-in successfully!", HttpStatus.OK);
    }
}
