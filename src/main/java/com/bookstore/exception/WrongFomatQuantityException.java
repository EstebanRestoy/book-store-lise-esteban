package com.bookstore.exception;

public class WrongFomatQuantityException extends RuntimeException {
    public WrongFomatQuantityException(String quantity) {
        super("la quantité :" + quantity + "n'a pas un format valide !");
    }
}
