package com.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.entity.Book;
import com.bookstore.serialization.Stub;
import com.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/books")
    public List<Book> books() {
        return bookService.findAll();
    }
}