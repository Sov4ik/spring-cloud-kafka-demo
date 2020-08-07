package com.bookshop.usermicroservice.service;

import com.bookshop.usermicroservice.dao.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private final MessageChannel messageChannel;

    public Sender(@Qualifier("output") MessageChannel messageChannel){
        this.messageChannel = messageChannel;
    }

    Logger logger = LoggerFactory.getLogger(Sender.class);

    public void sendMessage(User user){
        messageChannel.send(MessageBuilder.withPayload(user).build());
        logger.info("Success send = '{}'", user.getUsername());
    }
}
