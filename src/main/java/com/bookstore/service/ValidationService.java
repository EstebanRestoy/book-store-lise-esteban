package com.bookstore.service;

import com.bookstore.exception.ISBNNotValidException;
import com.bookstore.exception.QuantityNotAcceptableException;
import com.bookstore.exception.WrongFomatQuantityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ValidationService implements IValidationService{


    @Autowired
    private Environment env;

    @Override
    public void isValidISBN(String isbn) {
        if(!(isbn.matches("[0-9]{12,13}"))){
            throw new ISBNNotValidException(isbn);
        };
    }


    @Override
    public void isValidStock(String stock) {
        int stockInteger = 0;
        try {
            stockInteger = Integer.parseInt(stock);
        }catch(Exception e) {
             throw new WrongFomatQuantityException(stock);
        }
        if(stockInteger <= 0)
            throw new QuantityNotAcceptableException(stock);

    }

}
