package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.Payloads.LoginRequest;
import com.bookshop.daomicroservice.Payloads.UserPayload;
import com.bookshop.daomicroservice.dao.User;
import com.bookshop.daomicroservice.messages.Message;
import com.bookshop.daomicroservice.messages.MessageSender;
import com.bookshop.daomicroservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

@Service
@EnableBinding(Source.class)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final MessageSender messageSender;

    private final ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository,
                           MessageSender messageSender,
                           ObjectMapper objectMapper){

        this.userRepository = userRepository;
        this.messageSender = messageSender;
        this.objectMapper = objectMapper;
    }

    @Override
    @StreamListener(value = "input", condition = "headers['type']=='SaveUser'")
    @Transactional
    public void saveUser(String messageJson) throws IOException {

        Message<UserPayload> message = objectMapper.readValue(messageJson, new TypeReference<>(){});
        UserPayload messageData = message.getData();

        logger.info("Success recived = '{}'", messageData.getUsername());
        //userRepository.save(messageData);
    }

    @Override
    @StreamListener(value = "input", condition = "headers['type']=='FindByUsername'")
    @Transactional
    public void findByUsername(String messageJson) throws IOException {

        Message<String> message = objectMapper.readValue(messageJson, new TypeReference<>(){});
        String messageData = message.getData();

        logger.info("Success recived = '{}'", messageData);
        try {

            User user = userRepository.findByUsername(messageData).orElseThrow(() -> new RuntimeException("USer not found"));
            messageSender.send(new Message<>("FindByUsername", user));

        }catch (RuntimeException e){

            logger.error("Error '{}'", e.getLocalizedMessage());
            messageSender.send(new Message<>("FindByUsername", "Fail"));
        }
    }


    @Override
    @StreamListener(value = "input", condition = "headers['type']=='existsByEmail'")
    @Transactional
    public void existsByEmail(String messageJson) throws IOException {

        Message<String> message = objectMapper.readValue(messageJson, new TypeReference<>(){});
        String messageData = message.getData();

        logger.info("Success recived = '{}'", messageData);
        try {

            userRepository.findByUsername(messageData).orElseThrow(() -> new RuntimeException("USer not found"));
            messageSender.send(new Message<>("existsByEmail", "Success"));

        }catch (RuntimeException e){

            logger.error("Error '{}'", e.getLocalizedMessage());
            messageSender.send(new Message<>("existsByEmail", "Fail"));
        }
    }


}
