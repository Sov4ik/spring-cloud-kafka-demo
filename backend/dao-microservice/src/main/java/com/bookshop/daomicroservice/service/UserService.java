package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.messages.Message;

import java.io.IOException;

public interface UserService {

    void saveUser(String messageJson) throws IOException;

    Message<?> findByUsername(Message<String> messageJson) throws IOException;

    Message<?> existsByEmail(Message<String> messageJson) throws IOException;

    Message<?> existByUsername(Message<String> messageJson) throws IOException;
}
