package org.example.demospringbatch.models;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Calendar;

public class Customer {
    private int id ;
    private String name;

    private Calendar birthday;

    private int transactions;

    public int getTransactions() {
        return transactions;
    }

    public void setTransactions(int transactions) {
        this.transactions = transactions;
    }

    public Customer(int id, String name, Calendar birthday, int transactions) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.transactions = transactions;
    }

    public Customer(int id, String name, Calendar birthday) {
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

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }
}
