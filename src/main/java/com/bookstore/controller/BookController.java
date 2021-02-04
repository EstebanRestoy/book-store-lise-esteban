package com.bookstore.controller;


import java.util.List;
import java.util.Optional;

import com.bookstore.entity.Book;
import com.bookstore.exception.BookNotFound;
import com.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/books")
    public List<Book> books() {
        return bookService.findAll();
    }

    @GetMapping("/book/{isbn}")
    public Book books(@PathVariable("isbn") String isbn) throws Exception {
        Optional<Book> result = bookService.findOneByISBN(isbn);
        if(result.isPresent())
            return result.get();
        throw new BookNotFound("Book with ISBN : "+ isbn + " not found");
    }

}