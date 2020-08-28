package com.bookshop.apigateway.service;

import com.bookshop.apigateway.models.User;
import com.bookshop.apigateway.payload.request.LoginRequest;
import com.bookshop.apigateway.payload.request.SignupRequest;
import com.bookshop.apigateway.payload.response.JwtResponse;
import com.bookshop.apigateway.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder encoder;

    private static final User user = new User();

    @BeforeEach
    void setUp(){
        user.setUsername("Test");
        user.setPassword(encoder.encode("test"));
        user.setEmail("test@test.com");
    }

    @Test
    void authenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getPassword());
    }

    @Test
    @Transactional
    void registerUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(user.getEmail()+"new");
        signupRequest.setUsername(user.getUsername()+"new");
        signupRequest.setPassword(user.getPassword());
        Assertions.assertEquals(200, authService.registerUser(signupRequest).getStatusCodeValue());
    }
}
