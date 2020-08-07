package com.bookshop.usermicroservice.controller;

import com.bookshop.usermicroservice.dao.User;
import com.bookshop.usermicroservice.service.Sender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final Sender sender;

    public UserController(Sender sender){
        this.sender = sender;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        sender.sendMessage(user);
        return ResponseEntity.ok().body("YES");
    }

}
