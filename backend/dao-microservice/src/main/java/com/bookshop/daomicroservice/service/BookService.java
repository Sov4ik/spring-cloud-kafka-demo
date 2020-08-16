package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface BookService {

    Message<?> findAll(Message<Object> messageJson);

    void deleteBook(String messageJson) throws JsonProcessingException;

    Message<?> findById(Message<Object> messageJson);

    void saveBook(String messageJson) throws IOException;
}
