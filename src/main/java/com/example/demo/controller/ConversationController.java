package com.example.demo.controller;

import com.example.demo.model.ConversationDTO;
import com.example.demo.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping(value="/get")
    public ResponseEntity<Object> getConversationsOfUser(HttpServletRequest request){
        Long userId = (long) -1;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                userId = Long.valueOf(cookie.getValue());
            }
        }
        return new ResponseEntity<>(conversationService.getConversationsOfUser(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createConversation(@RequestBody ConversationDTO conversationDTO){
        return new ResponseEntity<>(conversationService.createConversation(conversationDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<Object> deleteConversation(@PathVariable Long id){
        conversationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
