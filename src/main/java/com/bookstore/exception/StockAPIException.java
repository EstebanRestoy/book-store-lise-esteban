package com.bookstore.exception;

public class StockAPIException extends RuntimeException {
    public StockAPIException(String message) {
        super(message);
    }
}
