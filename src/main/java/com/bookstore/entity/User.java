package com.bookstore.entity;

import javax.persistence.*;

@Table(name = "t_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    private String name;
    private String token;

    public User() {
    }

    public User(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
