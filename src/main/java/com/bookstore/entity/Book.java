package com.bookstore.entity;


import javax.persistence.*;

@Table(name = "book")
@Entity
public class Book {

    @Id
    private String isbn; //unique 22 max

    private String name;

    private int quantity = 0;

    public Book() {

    }

    public Book(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
