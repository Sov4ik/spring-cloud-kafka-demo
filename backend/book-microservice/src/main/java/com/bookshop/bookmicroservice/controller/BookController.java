package com.bookshop.bookmicroservice.controller;


import com.bookshop.bookmicroservice.models.Book;
import com.bookshop.bookmicroservice.service.BookService;
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

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getEmployeeById(@PathVariable(value = "id") String bookId) {

        return bookService.getBookById(bookId);
    }

    @PostMapping("/books")
    public boolean createBook(@Valid @RequestBody Book book) {
        //bookService.save(book);
        return true;
    }

    @PutMapping("/books/{id}")
    public boolean updateBook(@PathVariable(value = "id") Long bookId,
                                                   @Valid @RequestBody Book bookDetails){
        //bookService.updateBook(bookId, bookDetails);
        return true;
    }

    @DeleteMapping("/books/{id}")
    public boolean deleteBook(@PathVariable(value = "id") Long bookId){
        //bookService.deleteBook(bookId);
        return true;
    }
}
