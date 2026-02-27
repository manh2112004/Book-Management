package org.example.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.example.bookservice.command.data.Book;
import org.example.bookservice.command.data.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;
    @EventHandler
    public void on(BookCreatedEvent event){
        Book book=new Book();
        BeanUtils.copyProperties(event,book);
        bookRepository.save(book);
    }
    @EventHandler
    public void on(BookUpdatedEvent event){
        Optional<Book> oldBook=bookRepository.findById(event.getId());
        if(oldBook.isPresent()){
            Book book=oldBook.get();
            book.setAuthor(event.getAuthor());
            book.setName(event.getName());
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        }
    }
    @EventHandler
    public void on(BookDeleteEvent event){
        Optional<Book> oldBook=bookRepository.findById(event.getId());
        if(oldBook.isPresent()){
            bookRepository.delete(oldBook.get());
        }
    }
}
