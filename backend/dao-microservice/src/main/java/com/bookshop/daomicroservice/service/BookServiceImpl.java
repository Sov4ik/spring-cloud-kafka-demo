package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.dao.Book;
import com.bookshop.daomicroservice.messages.Message;
import com.bookshop.daomicroservice.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    private final ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository,
                           ObjectMapper objectMapper){

        this.bookRepository = bookRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @StreamListener(value = "input", condition = "headers['type']=='saveBook'")
    @Transactional
    public void saveBook(String messageJson) throws IOException {

        Message<Book> message = objectMapper.readValue(messageJson, new TypeReference<>(){});
        Book messageData = message.getData();

        logger.info("Success recived = '{}'", messageData.getName());
        bookRepository.save(messageData);
    }

    @Override
    @KafkaListener(topics = "${kafka.requestbook.topic}",  topicPartitions = {
            @TopicPartition(topic = "${kafka.requestbook.topic}", partitions = "1")
    })
    @SendTo
    public Message<?> findById(Message<Object> messageJson) {

        logger.info("Success received '[{}]'", messageJson.getData());

        if (bookRepository.findBookById(Long.parseLong(messageJson.getData().toString())).isPresent())
        messageJson.setData(bookRepository.findBookById((Long.parseLong(messageJson.getData().toString()))).get());

        else messageJson.setData("Fail");

        logger.info("Success send [{}]", messageJson.getData());

        return messageJson;
    }


    @Override
    @KafkaListener(topics = "${kafka.requestbook.topic}",  topicPartitions = {
            @TopicPartition(topic = "${kafka.requestbook.topic}", partitions = "0")
    })
    @SendTo
    public Message<?> findAll(Message<Object> messageJson) {

        logger.info("Success received '[{}]'", messageJson.getData());

        if(!bookRepository.findAll().isEmpty()) messageJson.setData(bookRepository.findAll());
        else messageJson.setData("Fail");

        logger.info("Success send [{}]", messageJson.getData());

        return messageJson;
    }

    @Override
    @StreamListener(value = "input", condition = "headers['type']=='deleteBook'")
    @Transactional
    public void deleteBook(String messageJson) throws JsonProcessingException {

        Message<Book> message = objectMapper.readValue(messageJson, new TypeReference<>(){});
        Book messageData = message.getData();

        logger.info("Success received [{}]", messageData.getName());

        bookRepository.deleteById(messageData.getId());

        logger.info("Success delete [{}]", messageData.getName());
    }
}
