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

    /**
     * Authentication authenticate = authenticationManager.authenticate(...)
     * It attempts to authenticate the user by using the authenticationManager.
     * It creates an Authentication object with the provided UsernamePasswordAuthenticationToken,
     * which contains the user's login credentials.
     *
     * SecurityContextHolder.getContext().setAuthentication(authenticate)
     * After successful authentication, the authenticated Authentication object is set in the security context.
     * This step effectively logs the user in.
     *
     * @param userCredentialsDTO obiect de transfer continand credentialele care trebuie verificate
     * @return HTTP STATUS CREATED
     */
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
        userCookie.setMaxAge(-1); // cookie valabil pe durata sesiunii
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