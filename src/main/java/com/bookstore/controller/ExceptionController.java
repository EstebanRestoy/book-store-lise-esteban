package com.bookstore.controller;

import java.util.Date;
import com.bookstore.entity.ApiException;
import com.bookstore.exception.*;
import com.bookstore.service.IBookService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private IBookService bookService;

    @ExceptionHandler(BookNotFound.class)
    public final ResponseEntity<ApiException> handleBookNotFoundException(BookNotFound ex, WebRequest request) {
        ApiException e = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ISBNNotValidException.class)
    public final ResponseEntity<ApiException> handleISBNNotValidException(ISBNNotValidException ex, WebRequest request) {
        ApiException e = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuantityNotAcceptableException.class)
    public final ResponseEntity<ApiException> handleQuantityNotAcceptableException(QuantityNotAcceptableException ex, WebRequest request) {
        ApiException e = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongFomatQuantityException.class)
    public final ResponseEntity<ApiException> handleWrongFomatQuantityException(WrongFomatQuantityException ex, WebRequest request) {
        ApiException e = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StockAPIException.class)
    public final ResponseEntity<ApiException> handleStockAPIException(StockAPIException ex, WebRequest request) {
        Gson g = new Gson();
        ApiException apiException = g.fromJson(ex.getMessage().substring(7,ex.getMessage().length()-1), ApiException.class);
        return new ResponseEntity<>(apiException, HttpStatus.SERVICE_UNAVAILABLE);
    }

}