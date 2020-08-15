package com.bookshop.bookmicroservice.service;

import com.bookshop.bookmicroservice.messages.MessageProvider;
import com.bookshop.bookmicroservice.models.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final MessageProvider<Object> messageProvider;

    public BookService(MessageProvider<Object> messageProvider){
        this.messageProvider = messageProvider;

    }

    public List<Book> findAll(){
        return (List<Book>) messageProvider.sendAndReceived(null, 0).getData();
    }

    @Transactional
    public ResponseEntity<Book> getBookById(String bookId){
       return  ResponseEntity.ok().body((Book) messageProvider.sendAndReceived(bookId, 1).getData());
    }

    /*public ResponseEntity<?> save(Book book) {

    }*/

   /* @Transactional
    public ResponseEntity<Book> updateBook(Long bookId,
                                           Book bookDetails){

        return ResponseEntity.ok(updatedEmployee);
    }


    @Transactional
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId)
        throws ResourceNotFoundException {

            return response;
    }*/
}
