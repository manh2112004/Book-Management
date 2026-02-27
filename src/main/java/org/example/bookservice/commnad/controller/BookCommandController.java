package org.example.bookservice.commnad.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.bookservice.commnad.command.CreateBookCommand;
import org.example.bookservice.commnad.model.BookRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @PostMapping
    public String addBook(@RequestBody BookRequestModel model){
        CreateBookCommand command=new CreateBookCommand( UUID.randomUUID().toString(),
                model.getName(),
                model.getAuthor(),
                model.getIsReady());
        return commandGateway.sendAndWait(command);
    }
}
