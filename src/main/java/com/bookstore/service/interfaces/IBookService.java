package com.bookstore.service.interfaces;


import com.bookstore.entity.Book;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> findAll();
    Optional<Book> findOneByISBN(String isbn);
    void orderBook(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException;
    void addStock(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException;
    void removeStock(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException;
    int createOrder(String isbn, int quantity) throws HttpClientErrorException, HttpServerErrorException;

}