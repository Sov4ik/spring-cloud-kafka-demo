package com.bookshop.apigateway.Messages;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class MessageProvider<T> {

    private final ReplyingKafkaTemplate<String, Message<?>, Message<?>> kafkaTemplate;

    public MessageProvider(ReplyingKafkaTemplate<String, Message<?>, Message<?>> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${kafka.request.topic}")
    String requestTopic;

    @Value("${kafka.reply.topic}")
    String requestReplyTopic;

    private static final Logger logger = LoggerFactory.getLogger(MessageProvider.class);

    public Message<?> sendAndReceived(T data, int partition)  {

        Message<T> message = new Message<>();

        message.setData(data);

        logger.info("success send [{}]", message.getData());

        ProducerRecord<String, Message<?>> record = new ProducerRecord<>(requestTopic, partition, null, message);

        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        RequestReplyFuture<String, Message<?>, Message<?>> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        ConsumerRecord<String, Message<?>> consumerRecord = null;

        try {
            SendResult<String, Message<?>> sendResult = sendAndReceive.getSendFuture().get();
            logger.info("ProducerRecord request[{}]", sendResult.getProducerRecord());

            consumerRecord = sendAndReceive.get();
            logger.info("ConsumerRecord request[{}]", consumerRecord);
        }catch (Exception e){
            logger.info("Failed to received response[{}]", e.getLocalizedMessage());
        }

        assert consumerRecord != null;

        return consumerRecord.value();
    }

}
