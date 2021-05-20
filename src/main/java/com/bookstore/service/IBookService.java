package com.bookstore.service;


import com.bookstore.entity.Book;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> findAll();
    Optional<Book> findOneByISBN(String isbn);
    void OrderBook(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException;
    void AddStock(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException;
    void RemoveStock(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException;
    int CreateOrder(String isbn, int quantity) throws HttpClientErrorException, HttpServerErrorException;


}