package org.example.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.example.bookservice.command.data.Book;
import org.example.bookservice.command.data.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
