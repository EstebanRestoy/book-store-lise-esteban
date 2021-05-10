package com.bookstore.exception;

public class ISBNNotValidException extends RuntimeException {
    public ISBNNotValidException(String isbn) {
        super("l'isbn : " + isbn + " n'est pas valid !");
    }
}
