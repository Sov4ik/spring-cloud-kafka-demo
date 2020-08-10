package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.Payloads.UserPayload;
import com.bookshop.daomicroservice.dao.User;
import com.bookshop.daomicroservice.messages.Message;
import com.bookshop.daomicroservice.messages.MessageSender;
import com.bookshop.daomicroservice.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @KafkaListener(topics = "${kafka.request.topic}",  topicPartitions = {
            @TopicPartition(topic = "${kafka.request.topic}", partitions = "3")
    })
    @SendTo
    public Message<?> findByUsername(Message<String> messageJson) {
        
        logger.info("Success received '[{}]'", messageJson);

        Message<User> message = new Message<>();

        try {
            message.setData(userRepository.findByUsername(messageJson.getData()).orElseThrow(() -> new RuntimeException("User not found")));
        } catch (RuntimeException e){
            logger.info("Failed to find user [{}]", e.getLocalizedMessage());
        }
        logger.info("Success send [{}]", messageJson.getData());

        return message;
    }


    @KafkaListener(topics = "${kafka.request.topic}",  topicPartitions = {
            @TopicPartition(topic = "${kafka.request.topic}", partitions = "0")
    })
    @SendTo
    public Message<?> existByUsername(Message<String> messageJson) {

        logger.info("Success received '[{}]'", messageJson);

        if(userRepository.existsByUsername(messageJson.getData())) messageJson.setData("Fail");
        else messageJson.setData("Success");

        logger.info("Success send [{}]", messageJson.getData());

        return messageJson;
    }

    @KafkaListener(topics = "${kafka.request.topic}",  topicPartitions = {
            @TopicPartition(topic = "${kafka.request.topic}", partitions = "1")
    })
    @SendTo
    public Message<?> existsByEmail(Message<String> messageJson){

        logger.info("Success received [{}]", messageJson.getData());

        if(userRepository.existsByEmail(messageJson.getData())) messageJson.setData("Fail");
        else messageJson.setData("Success");

        logger.info("Success send [{}]", messageJson.getData());
        return messageJson;
    }

}
