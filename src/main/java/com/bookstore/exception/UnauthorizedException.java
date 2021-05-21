package com.bookstore.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("user not autorized");
    }
}
