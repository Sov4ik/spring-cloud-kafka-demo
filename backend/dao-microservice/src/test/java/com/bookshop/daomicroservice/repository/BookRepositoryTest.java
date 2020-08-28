package com.bookshop.daomicroservice.repository;

import com.bookshop.daomicroservice.dao.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private static final Book book = new Book();

    @BeforeEach
    void init(){
        book.setActive(true);
        book.setId(10L);
        book.setDescription("Some description");
        book.setUnitsInStock(10);
        book.setUnitPrice(BigDecimal.ONE);
    }

    @Test
    @Transactional
    @Rollback
    void findBookById() {
        bookRepository.save(book);
        Assertions.assertNotNull(bookRepository.findBookById(10L));
    }

    @Test
    @Transactional
    @Rollback
    void findAll(){
        bookRepository.save(book);
        Assertions.assertNotNull(bookRepository.findAll());
    }
}
