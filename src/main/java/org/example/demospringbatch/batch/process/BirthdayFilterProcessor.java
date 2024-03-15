package org.example.demospringbatch.batch.process;

import org.example.demospringbatch.models.Customer;
import org.springframework.batch.item.ItemProcessor;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BirthdayFilterProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(final Customer item) throws Exception {
        if (new GregorianCalendar().get(Calendar.MONTH) == item.getBirthday().get(Calendar.MONTH)) {
            return item;
        }
        return null;
    }
}