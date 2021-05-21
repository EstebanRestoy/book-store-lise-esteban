package com.bookstore.service;

import com.bookstore.controller.BookController;
import com.bookstore.entity.Book;
import com.bookstore.exception.StockAPIException;
import com.bookstore.exception.WholeSalerAPIException;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.interfaces.IBookService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BookService implements IBookService {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository repository;

    @Autowired
    private Environment env;

    @Override
    public List<Book> findAll() {
        return (List<Book>) repository.findAll();
    }

    @Override
    public Optional<Book> findOneByISBN(String isbn) {
        return repository.findById(isbn);
    }

    @Override
    public void removeStock(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(Objects.requireNonNull(env.getProperty("post.remove.stock.api.url")),new HashMap<String, String>(){{
            put("isbn",isbn);
            put("quantity",quantity);
            put("key",env.getProperty("stock.api.key"));
        }},String.class);
    }

    @Override
    public void addStock(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(Objects.requireNonNull(env.getProperty("post.add.stock.api.url")),new HashMap<String, String>(){{
            put("isbn",isbn);
            put("quantity",quantity);
            put("key",env.getProperty("stock.api.key"));
        }},String.class);
    }

    @Override
    public void orderBook(String isbn, String quantity)throws HttpClientErrorException, HttpServerErrorException {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.getForEntity(
                Objects.requireNonNull(env.getProperty("get.stock.api.url") + "/" + isbn),
                String.class
        );

        if(200 != result.getStatusCodeValue())
            throw new StockAPIException("Error in stock get request");

        JsonObject convertedObject = new Gson().fromJson(result.getBody(), JsonObject.class);

        int quantityAvailable = convertedObject.get("quantity").getAsInt();
        int quantityToAskFor  = Integer.parseInt(quantity) - quantityAvailable;

        try{
           int qtAded = createOrder(isbn, quantityToAskFor);
            logger.info(String.valueOf(qtAded - quantityToAskFor + quantityAvailable));
           if(qtAded - quantityToAskFor + quantityAvailable != 0)
               addStock(isbn, String.valueOf(qtAded - quantityToAskFor));
        }
        catch (HttpClientErrorException e){
            throw new WholeSalerAPIException(e.getMessage());
        }
    }

    @Override
    public int createOrder(String isbn, int quantityToAskFor)throws HttpClientErrorException, HttpServerErrorException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ env.getProperty("wholesaler.api.key"));

        HttpEntity<Map> request = new HttpEntity<>(new HashMap<String, Object>(){{
            put("isbn",isbn);
            put("quantity", quantityToAskFor);
        }},headers);

        try{
            ResponseEntity<String> response = new RestTemplate().exchange(env.getProperty("wholesaler.make.order.api.url"), HttpMethod.PUT, request, String.class);
            if(response.getStatusCode() == HttpStatus.CREATED){
                Gson g = new Gson();
                JsonObject convertedObject = new Gson().fromJson(response.getBody(), JsonObject.class);
                logger.info("quantity order" + convertedObject.get("quantity").getAsString());
                return convertedObject.get("quantity").getAsInt();
            }
            throw new WholeSalerAPIException(response.getBody());
        }
        catch(Exception ex) {
            throw new WholeSalerAPIException(ex.getMessage());
        }
    }

}