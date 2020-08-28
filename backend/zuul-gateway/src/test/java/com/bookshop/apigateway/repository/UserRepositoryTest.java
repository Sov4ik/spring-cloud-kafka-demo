package com.bookshop.apigateway.repository;

import com.bookshop.apigateway.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private static final User user = new User();

    @BeforeEach
    void setUp(){
        user.setUsername("test2");
        user.setEmail("test2@test.com");
        user.setPassword(encoder.encode("test2"));
    }

    @Test
    @Transactional
    @Rollback
    void findByUsername() {
        userRepository.save(user);
        Assertions.assertFalse(userRepository.findByUsername(user.getUsername()).isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void existsByUsername() {
        userRepository.save(user);
        Assertions.assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    @Transactional
    @Rollback
    void existsByEmail() {
        userRepository.save(user);
        Assertions.assertTrue(userRepository.existsByEmail(user.getEmail()));
    }
}
