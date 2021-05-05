package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository repository;

    @Override
    public List<Book> findAll() {
        return (List<Book>) repository.findAll();
    }

    @Override
    public Optional<Book> findOneByISBN(String isbn) {
        return repository.findById(isbn);
    }

    @Override
    public void OrderBook(String isbn) {
        //APPEL LE SERVICE DE COMMANDE
    }
}