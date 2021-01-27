package com.bookstore.service;


import com.bookstore.entity.Book;
import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> findAll();
    Optional<Book> findOneById(Long id);

}