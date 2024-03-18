package org.example.demospringbatch.batch.process;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.models.Customer;
import org.hibernate.annotations.Comment;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

@Slf4j
@Component
public class BirthdayFilterProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(final Customer item) throws Exception {
        log.info("Processing customer: {}", item);

        Calendar birthdayCalendar = new GregorianCalendar();
        System.out.println(birthdayCalendar);
        birthdayCalendar.setTime(item.getBirthday());
        Calendar currentCalendar = Calendar.getInstance();
        if (currentCalendar.get(Calendar.MONTH) == birthdayCalendar.get(Calendar.MONTH)) {
            log.info("Customer has a birthday this month!"+ item.getName());
            return item;
        }
        return null;
    }
}