package com.bookstore.serialization;

import com.bookstore.entity.Book;

import java.util.ArrayList;
import java.util.Arrays;

public class Stub {

    public static ArrayList<Book> getBooks() {
        return new ArrayList<Book>(Arrays.asList(
                new Book("9782765409120", "Livre 1", "Emile Zola"),
                new Book("154871564789", "Livre 2", "Inconnu Inconnu")
        ));
    }
}
