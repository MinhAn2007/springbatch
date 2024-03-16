package org.example.demospringbatch.batch.process;

import org.example.demospringbatch.models.Customer;
import org.springframework.batch.item.ItemProcessor;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BirthdayFilterProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(final Customer item) throws Exception {
        Calendar birthdayCalendar = new GregorianCalendar();
        birthdayCalendar.setTime(item.getBirthday());

        // Get current month
        Calendar currentCalendar = Calendar.getInstance();

        if (currentCalendar.get(Calendar.MONTH) == birthdayCalendar.get(Calendar.MONTH)) {
            return item;
        }
        return null;
    }
}