package org.example.demospringbatch.models;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Customer {
    private int id ;
    private String name;

    private LocalDate birthday;

    public Customer(int id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
