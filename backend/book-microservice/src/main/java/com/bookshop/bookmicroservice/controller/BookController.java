package com.bookshop.bookmicroservice.controller;


import com.bookshop.bookmicroservice.models.Book;
import com.bookshop.bookmicroservice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBook() {
        return bookService.findAll();
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") String bookId) {
        Logger logger = LoggerFactory.getLogger(BookController.class);
        logger.info ("AAA");

        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping("/saveBook")
    public boolean createBook(@Valid @RequestBody Book book) {
        //bookService.save(book);
        return true;
    }

    @PutMapping("/updateBooks/{id}")
    public boolean updateBook(@PathVariable(value = "id") Long bookId,
                                                   @Valid @RequestBody Book bookDetails){
        //bookService.updateBook(bookId, bookDetails);
        return true;
    }

    @DeleteMapping("/deleteBooks/{id}")
    public boolean deleteBook(@PathVariable(value = "id") Long bookId){
        //bookService.deleteBook(bookId);
        return true;
    }
}
