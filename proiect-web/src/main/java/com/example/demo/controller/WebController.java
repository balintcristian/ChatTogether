package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/account")
    public String account(){
        return "account_details";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
