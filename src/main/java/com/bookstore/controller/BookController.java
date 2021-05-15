package com.bookstore.controller;


import java.util.*;

import com.bookstore.entity.Book;
import com.bookstore.exception.BookNotFound;
import com.bookstore.exception.StockAPIException;
import com.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private IBookService bookService;

    @Autowired
    private Environment env;

    @GetMapping("/books")
    public List<Book> books() {
        return bookService.findAll();
    }

    @GetMapping("/book/{isbn}")
    public Book books(@PathVariable("isbn") String isbn) throws Exception {
        Optional<Book> result = bookService.findOneByISBN(isbn);
        if (result.isPresent())
            return result.get();
        throw new BookNotFound("Book with ISBN : " + isbn + " not found");
    }

    @GetMapping("/buyBook")
    public String buyBook(@RequestParam("isbn") String isbn,
                          @RequestParam("quantity") String quantity) throws StockAPIException, BookNotFound {
        Optional<Book> result = bookService.findOneByISBN(isbn);
        // TODO authentification jwt
        if (result.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                String response = restTemplate.postForObject(Objects.requireNonNull(env.getProperty("post.remove.stock.api.url")), new HashMap<String, String>() {{
                    put("isbn", isbn);
                    put("quantity", quantity);
                    put("key", env.getProperty("stock.api.key"));
                }}, String.class);

                logger.info(response);
                return response;
            } catch (Exception e) {
                throw new StockAPIException(e.getMessage());
            }
        }
        throw new BookNotFound("Book with ISBN : " + isbn + " not found");
    }

    // TODO end point that list isbn to check isbn in whole saler service
}