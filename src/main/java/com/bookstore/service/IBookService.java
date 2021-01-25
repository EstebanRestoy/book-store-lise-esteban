package com.bookstore.service;


import com.bookstore.entity.Book;
import java.util.List;

public interface IBookService {
    List<Book> findAll();
}