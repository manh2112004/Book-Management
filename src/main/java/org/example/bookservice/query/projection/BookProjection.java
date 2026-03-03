package org.example.bookservice.query.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.example.bookservice.command.data.Book;
import org.example.bookservice.command.data.BookRepository;
import org.example.bookservice.query.model.BookResponseModel;
import org.example.bookservice.query.queries.GetAllBookQuery;
import org.example.bookservice.query.queries.GetDetailQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query){
        List<Book> list=bookRepository.findAll();
        List<BookResponseModel> listBookResponse=new ArrayList<>();
        list.forEach(book -> {
            BookResponseModel model=new BookResponseModel();
            BeanUtils.copyProperties(book,model);
            listBookResponse.add(model);
        });
        return listBookResponse;
    }

    @QueryHandler
    public BookResponseModel handle(GetDetailQuery query) throws Exception {
        BookResponseModel bookResponseModel=new BookResponseModel();
        Book book=bookRepository.findById(query.getId())
                .orElseThrow(()->new Exception("not found Book with BookId:"+query.getId()));
        BeanUtils.copyProperties(book,bookResponseModel);
        return bookResponseModel;
    }
}
