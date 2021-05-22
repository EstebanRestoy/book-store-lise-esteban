package com.bookstore.controller;


import java.util.*;

import com.bookstore.entity.Book;
import com.bookstore.exception.*;
import com.bookstore.service.interfaces.IAuthService;
import com.bookstore.service.interfaces.IBookService;
import com.bookstore.service.interfaces.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;


@RestController
public class BookController {

    private static final String SUCCES_BUY_MESSAGE = "l'achat a été realisé avec succés !";
    private static final String WELCOME_MESSAGE = "Welcome on the Shopping API Service";

    @Autowired
    private IBookService bookService;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IValidationService ValidationService;


    @GetMapping("/")
    public String welcome() {
        return WELCOME_MESSAGE;
    }

    @GetMapping("/books")
    public List<Book> books() {
        return bookService.findAll();
    }

    @GetMapping("/book/{isbn}")
    public Book books(@PathVariable("isbn") String isbn) throws Exception {
        Optional<Book> result = bookService.findOneByISBN(isbn);
        if (result.isPresent())
            return result.get();
        throw new BookNotFoundException(isbn);
    }

    @GetMapping(value = "/buyBook", produces = MediaType.TEXT_PLAIN_VALUE)
    public String buyBook(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer,
                          @RequestParam("isbn") String isbn,
                          @RequestParam("quantity") String quantity) throws BookNotFoundException, StockAPIException, ISBNNotValidException, WrongFomatQuantityException, QuantityNotAcceptableException {

        this.authService.isValidUserToken(bearer.replaceAll("Bearer", "").trim());

        ValidationService.isValidISBN(isbn);
        ValidationService.isValidStock(quantity);

        Optional<Book> result = bookService.findOneByISBN(isbn);
        if (result.isPresent()) {
            try {
                bookService.removeStock(isbn, quantity);
                return SUCCES_BUY_MESSAGE;
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                // cas ou ou il n'y a plus de stock disponible
                if (HttpStatus.UNPROCESSABLE_ENTITY.equals(e.getStatusCode())) {
                    bookService.orderBook(isbn, quantity);
                    return SUCCES_BUY_MESSAGE;
                }
                throw new StockAPIException(e.getMessage());
            }
        }
        throw new BookNotFoundException(isbn);
    }
}