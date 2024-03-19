package org.example.demospringbatch.models;

import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Getter
public class Customer implements Serializable {
    private int id ;
    private String name;

    private Date birthday;

    private int transactions;

    public void setTransactions(int transactions) {
        this.transactions = transactions;
    }

    public Customer(int id, String name, Date birthday, int transactions) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.transactions = transactions;
    }

    public Customer(int id, String name, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public Customer() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
