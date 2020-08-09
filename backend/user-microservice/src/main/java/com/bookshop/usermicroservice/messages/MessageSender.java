package com.bookshop.usermicroservice.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class MessageSender {

    private final MessageChannel messageChannel;

    private final ObjectMapper objectMapper;

    public MessageSender(@Qualifier("output") MessageChannel messageChannel,
                         ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.messageChannel = messageChannel;

    }

    public void send(Message<?> m) {
        try {

            String jsonMessage = objectMapper.writeValueAsString(m);

            messageChannel.send(
                    MessageBuilder.withPayload(jsonMessage).setHeader("type", m.getType()).build());
        } catch (Exception e) {
            throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
        }
    }
}
