package com.example.demo.controller;

import com.example.demo.model.MessageDTO;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/get/{conversationId}")
    public ResponseEntity<Object> findByConversationId(@PathVariable Long conversationId) {
        return new ResponseEntity<>(messageService.findByConversationId(conversationId), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Object> saveMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                messageDTO.setUserId(Long.valueOf(cookie.getValue()));
            }
        }
        return new ResponseEntity<>(messageService.saveMessage(messageDTO), HttpStatus.CREATED);
    }
}
