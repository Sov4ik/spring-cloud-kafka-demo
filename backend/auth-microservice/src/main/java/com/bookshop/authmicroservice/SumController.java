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

import java.util.concurrent.ExecutionException;

@RestController
public class SumController {

    @Autowired
    ReplyingKafkaTemplate<String, LoginRequest, LoginRequest> kafkaTemplate;


    @Value("${kafka.request.topic}")
    String requestTopic;

    @Value("${kafka.reply.topic}")
    String requestReplyTopic;

    private static final Logger logger = LoggerFactory.getLogger(SumController.class);

    @PostMapping(value = "/qwerty", produces= MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sum(@RequestBody LoginRequest request) throws InterruptedException, ExecutionException {

        logger.info("send request [{}]", request);
        //String requestJSON = objectMapper.writeValueAsString(request);
        ProducerRecord<String, LoginRequest> record = new ProducerRecord<>(requestTopic, request);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        RequestReplyFuture<String, LoginRequest, LoginRequest> sendAndReceive = kafkaTemplate.sendAndReceive(record);
        SendResult<String, LoginRequest> sendResult = sendAndReceive.getSendFuture().get();
        logger.info("ProducerRecord request[{}]", sendResult.getProducerRecord());

        ConsumerRecord<String, LoginRequest> consumerRecord = sendAndReceive.get();
        logger.info("ConsumerRecord request[{}]", consumerRecord);

        //String jsonResponse = consumerRecord.value();

        // return consumer value
        return ResponseEntity.ok().body(consumerRecord.value());
    }

}
