package com.bookstore.service;

import com.bookstore.controller.BookController;
import com.bookstore.entity.Book;
import com.bookstore.exception.StockAPIException;
import com.bookstore.exception.UnauthorizedException;
import com.bookstore.exception.WholeSalerAPIException;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.interfaces.IAuthService;
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
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Override
    public void isValidUserToken(String bearer) throws HttpClientErrorException, HttpServerErrorException {
        if (repository.findOneByToken(bearer) == null) {
            throw new UnauthorizedException();
        }
    }

}