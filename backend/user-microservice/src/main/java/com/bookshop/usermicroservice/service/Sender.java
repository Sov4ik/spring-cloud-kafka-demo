package com.bookshop.usermicroservice.service;

import com.bookshop.usermicroservice.dao.User;
import com.bookshop.usermicroservice.messages.Message;
import com.bookshop.usermicroservice.messages.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private final MessageSender messageSender;

    public Sender(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    Logger logger = LoggerFactory.getLogger(Sender.class);

    public void sendMessage(User user){
        messageSender.send(new Message<>("SaveUser", user));
        logger.info("Success send = '{}'", user.getUsername());
    }
}
