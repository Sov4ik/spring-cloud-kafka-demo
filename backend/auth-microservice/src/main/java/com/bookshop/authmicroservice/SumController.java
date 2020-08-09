package com.bookshop.authmicroservice;

import com.bookshop.authmicroservice.payload.request.LoginRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@RestController
public class SumController {

    @Autowired
    ReplyingKafkaTemplate<String, LoginRequest, LoginRequest> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    String requestReplyTopic;


    private static final Logger logger = LoggerFactory.getLogger(SumController.class);

    @PostMapping(value = "/qwerty", produces= MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sum(@RequestBody LoginRequest request) throws InterruptedException, ExecutionException {
        // create producer record
        logger.info("Get - " + request);

        // create producer record
        ProducerRecord<String, LoginRequest> record = new ProducerRecord<>(requestTopic, request);
        // post in kafka topic
        RequestReplyFuture<String, LoginRequest, LoginRequest> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        // confirm if producer produced successfully
         SendResult<String, LoginRequest> sendResult = sendAndReceive.getSendFuture().get();

        // print all headers
         sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        // get consumer record
        ConsumerRecord<String, LoginRequest> consumerRecord = sendAndReceive.get();
        // return consumer value

        logger.info("Recived - " + consumerRecord);
        // return consumer value
        return ResponseEntity.ok().body(request);
    }

}
