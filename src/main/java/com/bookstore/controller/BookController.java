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

    @GetMapping("/book/{id}")
    public Book books(@PathVariable("id") Long id) throws Exception {
        Optional<Book> result = bookService.findOneById(id);
        if(result.isPresent())
            return result.get();
        throw new BookNotFound("Book with ID : "+ id + " not found");
    }

}