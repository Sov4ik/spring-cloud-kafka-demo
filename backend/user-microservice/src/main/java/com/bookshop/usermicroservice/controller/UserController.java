package com.bookshop.usermicroservice.controller;

import com.bookshop.usermicroservice.dao.User;
import com.bookshop.usermicroservice.service.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    private final Sender sender;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(Sender sender){
        this.sender = sender;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        sender.sendMessage(user);
        return ResponseEntity.ok().body("YES");
    }

    @PostMapping("/qwerty")
    public ResponseEntity<?> sum(@RequestBody User request) throws InterruptedException, ExecutionException {
        // create producer record
        logger.info("Get - " + request);

        /*ProducerRecord<String, Message<?>> record = new ProducerRecord<>(requestTopic, request);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, Message<?>, Message<?>> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        // confirm if producer produced successfully
        SendResult<String, Message<?>> sendResult = sendAndReceive.getSendFuture().get();

        //print all headers
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + Arrays.toString(header.value())));

        // get consumer record
        ConsumerRecord<String, Message<?>> consumerRecord = sendAndReceive.get();

        logger.info("Recived - " + consumerRecord.value());*/
        // return consumer value
        return ResponseEntity.ok().body(request);
    }

}
