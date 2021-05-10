package com.bookstore.exception;

public class BookNotFound extends RuntimeException {
    public BookNotFound(String isbn) {
        super("Book with ISBN : "+ isbn + " not found");
    }
}
