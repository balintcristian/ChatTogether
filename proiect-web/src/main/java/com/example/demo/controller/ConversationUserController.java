package com.example.demo.controller;

import com.example.demo.model.ConversationUserDTO;
import com.example.demo.service.ConversationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/conversation_user")
public class ConversationUserController {

    @Autowired
    private ConversationUserService conversationUserService;

    @PostMapping(value = "/invite")
    public ResponseEntity<Object> invite(@RequestBody ConversationUserDTO conversationUserDTO) {
        return new ResponseEntity<>(conversationUserService.inviteToConversation(conversationUserDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/leave/{conversationId}")
    public ResponseEntity<Object> leave(@PathVariable Long conversationId, HttpServletRequest request){
        Long userId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                userId = Long.valueOf(cookie.getValue());
            }
        }
        conversationUserService.deleteByConversationIdAndUserId(conversationId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
