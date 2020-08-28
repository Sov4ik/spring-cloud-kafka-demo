package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.dao.Book;
import com.bookshop.daomicroservice.messages.Message;
import com.bookshop.daomicroservice.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookServiceTest{

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    ObjectMapper objectMapper;

    private static final List<Book> books = new LinkedList<>();
    private static final Book book = new Book();

    @BeforeEach
    void init(){

        book.setId(10L);
        books.add(book);

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(bookRepository.findBookById(Mockito.any())).thenReturn(Optional.of(book));
    }

    @Test
    void findAll() {
        Message<Object> message = new Message<>();
        message.setData("Hello");
        Assertions.assertEquals(books, bookService.findAll(message).getData());
    }

    @Test
    void deleteBook() throws JsonProcessingException {
        Message<Book> mess = new Message<>();
        mess.setData(book);
        String message = objectMapper.writeValueAsString(mess);
        bookService.deleteBook(message);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(book.getId());
    }

    @Test
    void findById(){
        Message<Object> mess = new Message<>();
        mess.setData(book);
        Assertions.assertEquals(book, bookService.findById(mess).getData());
        Mockito.verify(bookRepository, Mockito.times(1)).findBookById(book.getId());
    }

    @Test
    void saveBook() throws IOException {
        Message<Book> mess = new Message<>();
        mess.setData(book);
        String message = objectMapper.writeValueAsString(mess);
        bookService.saveBook(message);
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }
}
