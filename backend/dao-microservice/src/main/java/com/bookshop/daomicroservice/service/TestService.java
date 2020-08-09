package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.Payloads.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class TestService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ObjectMapper objectMapper;

    public TestService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topic.request-topic}", groupId = "requestreplygorup")
    @SendTo
    //@Transactional
    public LoginRequest existsByUsername(LoginRequest messageJson) throws JsonProcessingException {

        //LoginRequest message = objectMapper.readValue(messageJson, new TypeReference<>(){});

        //logger.info("Success recived 1 = '{}'", message);
        logger.info("Success recived 2 = '{}'", messageJson);
        try {

            //userRepository.findByUsername(messageData).orElseThrow(() -> new RuntimeException("USer not found"));
            return messageJson;

        }catch (RuntimeException e){

            logger.error("Error '{}'", e.getLocalizedMessage());
            return messageJson;
        }
    }

}
