package com.bookstore.controller;


import java.util.*;

import com.bookstore.entity.Book;
import com.bookstore.exception.*;
import com.bookstore.service.IBookService;
import com.bookstore.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;


@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private static final String SuccesBuyMessage = "l'achat a été realisé avec succés !";

    @Autowired
    private IBookService bookService;

    @Autowired
    private IValidationService ValidationService;


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

    @GetMapping("/buyBook")
    public String buyBook(@RequestParam("isbn") String isbn,
                          @RequestParam("quantity") String quantity) throws BookNotFoundException, StockAPIException, ISBNNotValidException, WrongFomatQuantityException, QuantityNotAcceptableException {
        // TODO authentification jwt
        // to check that user is authenticated
        // Validation des inputs
        ValidationService.isValidISBN(isbn);
        ValidationService.isValidStock(quantity);

        Optional<Book> result = bookService.findOneByISBN(isbn);
        if (result.isPresent()) {
            try {
                bookService.RemoveStock(isbn, quantity);
                return SuccesBuyMessage;
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                // cas ou ou il n'y a plus de stock disponible
                logger.info(e.getMessage(),e.getStatusCode());
                if (HttpStatus.UNPROCESSABLE_ENTITY.equals(e.getStatusCode())) {
                    bookService.OrderBook(isbn, quantity);
                    return SuccesBuyMessage;
                }
                throw new StockAPIException(e.getMessage());
            }
        }
        throw new BookNotFoundException(isbn);
    }
}