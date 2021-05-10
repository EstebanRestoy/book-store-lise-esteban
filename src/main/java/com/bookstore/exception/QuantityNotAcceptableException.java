package com.bookstore.exception;

public class QuantityNotAcceptableException extends RuntimeException {
    public QuantityNotAcceptableException(String quantity) {
        super("la quantité :" + quantity + "n'est pas une quantité valide !");
    }
}
