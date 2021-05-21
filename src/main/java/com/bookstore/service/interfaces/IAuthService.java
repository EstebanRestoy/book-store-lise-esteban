package com.bookstore.service.interfaces;


import com.bookstore.entity.Book;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

public interface IAuthService {
    void isValidUserToken(String bearer) throws HttpClientErrorException, HttpServerErrorException;
}