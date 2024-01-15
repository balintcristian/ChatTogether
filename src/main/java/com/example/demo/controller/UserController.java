package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getUserData(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Long userId = null;
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                userId = Long.valueOf(cookie.getValue());
            }
        }
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @PatchMapping(value = "/patch_nickname/{newNickname}")
    public ResponseEntity<Object> patchNickname(@PathVariable String newNickname, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Long userId = null;
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                userId = Long.valueOf(cookie.getValue());
            }
        }
        return new ResponseEntity<>(userService.patchNickname(userId, newNickname), HttpStatus.OK);
    }
}
