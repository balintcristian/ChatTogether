package com.example.demo.controller;

import com.example.demo.model.UserCredentialsDTO;
import com.example.demo.model.UserDataDTO;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")

    public ResponseEntity<String> login(@RequestBody UserCredentialsDTO userCredentialsDTO,
                                        HttpServletRequest request,
    HttpServletResponse response) {
        System.out.println("Received login request: " + userCredentialsDTO);
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userCredentialsDTO.getLoginUsername(),
                        userCredentialsDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDataDTO userDataDTO = userService.findByLoginUsername(userCredentialsDTO.getLoginUsername());
        Cookie userCookie = new Cookie("userId", String.valueOf(userDataDTO.getId()));
        userCookie.setMaxAge(-1); 
        userCookie.setPath("/");
        response.addCookie(userCookie);
        System.out.println("Sending response...");

        return ResponseEntity.ok("Login successful");
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Logout successfully!", HttpStatus.OK);
    }
}