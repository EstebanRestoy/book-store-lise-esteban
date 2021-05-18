package com.bookstore.service;

import com.bookstore.entity.ApiException;
import com.bookstore.entity.Book;
import com.bookstore.exception.StockAPIException;
import com.bookstore.repository.BookRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    public void BuyBook(String isbn, String quantity) throws HttpClientErrorException, HttpServerErrorException {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(Objects.requireNonNull(env.getProperty("post.remove.stock.api.url")),new HashMap<String, String>(){{
            put("isbn",isbn);
            put("quantity",quantity);
            put("key",env.getProperty("stock.api.key"));
        }},String.class);
    }

    @Override
    public void OrderBook(String isbn, String quantity)throws HttpClientErrorException, HttpServerErrorException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(Objects.requireNonNull(env.getProperty("get.stock.api.url") + "/" + isbn), String.class);

        if(200 != result.getStatusCodeValue())
            throw new StockAPIException("Error in stock get request");

        JsonObject convertedObject = new Gson().fromJson(result.getBody(), JsonObject.class);

        int quantityAvailable = convertedObject.get("quantity").getAsInt();
        int quantityToAskFor = Integer.parseInt(quantity) - quantityAvailable;

        try{
            restTemplate.postForObject(Objects.requireNonNull(env.getProperty("post.remove.stock.api.url")),new HashMap<String, String>(){{
                put("isbn",isbn);
                put("quantity", String.valueOf(quantityToAskFor));
                put("key",env.getProperty("stock.api.key"));
            }},String.class);
        }catch (HttpClientErrorException e){
            if(e.getRawStatusCode() == 422){

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer "+ env.getProperty("wholesaler.api.key"));

                HttpEntity<Map> request = new HttpEntity<>(new HashMap<String, String>(){{
                    put("isbn",isbn);
                    put("quantity", String.valueOf(quantityToAskFor));
                    put("key",env.getProperty("stock.api.key"));
                }});

                ResponseEntity<String> response = new RestTemplate().exchange(env.getProperty("wholesaler.make.order.api.url"), HttpMethod.PUT, request, String.class);
                if(response.getStatusCode() == HttpStatus.OK){
                    return;
                }
            }
        }
    }
}