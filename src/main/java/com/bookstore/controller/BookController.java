package com.bookstore.controller;

import java.util.ArrayList;

import com.bookstore.entity.Book;
import com.bookstore.serialization.Stub;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private ArrayList<Book> liste = Stub.getBooks();

    @GetMapping("/books")
    public ArrayList<Book> books() {
        return liste;
    }
}