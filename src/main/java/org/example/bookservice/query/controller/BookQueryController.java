package org.example.bookservice.query.controller;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.bookservice.query.model.BookResponseModel;
import org.example.bookservice.query.queries.GetAllBookQuery;
import org.example.bookservice.query.queries.GetDetailQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BookResponseModel>getAllBooks(){
        GetAllBookQuery query=new GetAllBookQuery();
        return queryGateway.query(query,ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }
    @GetMapping("/{bookId}")
    public BookResponseModel getDetail(@PathVariable String bookId){
        GetDetailQuery query=new GetDetailQuery(bookId);
        return queryGateway.query(query,ResponseTypes.instanceOf(BookResponseModel.class)).join();

    }
}
