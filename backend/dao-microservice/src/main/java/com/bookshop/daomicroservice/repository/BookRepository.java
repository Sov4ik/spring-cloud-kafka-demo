package com.bookshop.daomicroservice.repository;

import com.bookshop.daomicroservice.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookById(Long id);
}
