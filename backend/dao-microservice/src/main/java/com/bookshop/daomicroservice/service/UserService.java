package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.Payloads.LoginRequest;
import com.bookshop.daomicroservice.Payloads.UserPayload;
import com.bookshop.daomicroservice.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface UserService {

    void saveUser(String messageJson) throws IOException;

    void findByUsername(String messageJson) throws IOException;

    void existsByEmail(String messageJson) throws IOException;
}
