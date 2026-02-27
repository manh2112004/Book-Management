package org.example.bookservice.command.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.bookservice.command.command.CreateBookCommand;
import org.example.bookservice.command.command.DeleteBookCommand;
import org.example.bookservice.command.command.UpdateBookCommand;
import org.example.bookservice.command.event.BookCreatedEvent;
import org.example.bookservice.command.event.BookDeleteEvent;
import org.example.bookservice.command.event.BookUpdatedEvent;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Getter
@Setter
public class BookAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
    @CommandHandler
    public BookAggregate(CreateBookCommand command){
        BookCreatedEvent bookCreatedEvent=new BookCreatedEvent();
        BeanUtils.copyProperties(command,bookCreatedEvent);

        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand command){
        BookUpdatedEvent bookUpdatedEvent=new BookUpdatedEvent();
        BeanUtils.copyProperties(command,bookUpdatedEvent);

        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand command){
        BookDeleteEvent bookDeletedEvent=new BookDeleteEvent();
        BeanUtils.copyProperties(command,bookDeletedEvent);

        AggregateLifecycle.apply(bookDeletedEvent);
    }
    @EventSourcingHandler
    public void on(BookCreatedEvent event){
        this.id=event.getId();
        this.name=event.getName();
        this.author=event.getAuthor();
        this.isReady=event.getIsReady();
    }
    @EventSourcingHandler
    public void on(BookUpdatedEvent event){
        this.id=event.getId();
        this.name=event.getName();
        this.author=event.getAuthor();
        this.isReady=event.getIsReady();
    }
    @EventSourcingHandler
    public void on(BookDeleteEvent event){
        this.id=event.getId();
    }
}
