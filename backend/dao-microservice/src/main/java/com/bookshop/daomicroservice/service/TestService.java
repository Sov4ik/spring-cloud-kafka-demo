/*
package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.Payloads.LoginRequest;
import com.bookshop.daomicroservice.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;



@Service
public class TestService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ObjectMapper objectMapper;

    public TestService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.request.topic}",  topicPartitions = {
            @TopicPartition(topic = "${kafka.request.topic}", partitions = "0")
    })
    @SendTo
    public Message<?> onMessage(Message<?> req) throws JsonProcessingException {

        //LoginRequest message = objectMapper.readValue(messageJson, new TypeReference<>(){});

        //record.headers().add(KafkaHeaders.CORRELATION_ID, correlation);
        //logger.info("Success recived 1 = '{}'", message);
        logger.info("Success recived 2 = '{}'", req);

        try {
            req.setType("Other type");
            //userRepository.findByUsername(messageData).orElseThrow(() -> new RuntimeException("USer not found"));
            return req;

        }catch (RuntimeException e){

            logger.error("Error '{}'", e.getLocalizedMessage());
            return req;
        }
    }

    @KafkaListener(topics = "${kafka.request.topic}",  topicPartitions = {
            @TopicPartition(topic = "${kafka.request.topic}", partitions = "1")
    })
    @SendTo
    public Message<?> onMessage2(Message<?> req) throws JsonProcessingException {

        //LoginRequest message = objectMapper.readValue(messageJson, new TypeReference<>(){});

        //record.headers().add(KafkaHeaders.CORRELATION_ID, correlation);
        //logger.info("Success recived 1 = '{}'", message);
        logger.info("Success recived 2 = '{}'", req.getType());
        try {

            //userRepository.findByUsername(messageData).orElseThrow(() -> new RuntimeException("USer not found"));
            return req;

        }catch (RuntimeException e){

            logger.error("Error '{}'", e.getLocalizedMessage());
            return req;
        }
    }


}
*/
