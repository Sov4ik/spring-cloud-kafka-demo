package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.Payloads.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;



@Service
public class TestService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ObjectMapper objectMapper;

    public TestService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.request.topic}")
    @SendTo
    public LoginRequest onMessage(LoginRequest req) throws JsonProcessingException {

        //LoginRequest message = objectMapper.readValue(messageJson, new TypeReference<>(){});

        //record.headers().add(KafkaHeaders.CORRELATION_ID, correlation);
        //logger.info("Success recived 1 = '{}'", message);
        logger.info("Success recived 2 = '{}'", req);
        try {

            //userRepository.findByUsername(messageData).orElseThrow(() -> new RuntimeException("USer not found"));
            return req;

        }catch (RuntimeException e){

            logger.error("Error '{}'", e.getLocalizedMessage());
            return req;
        }
    }


}
