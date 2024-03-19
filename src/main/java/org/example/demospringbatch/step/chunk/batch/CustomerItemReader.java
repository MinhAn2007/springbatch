package org.example.demospringbatch.step.chunk.batch;


import org.example.demospringbatch.models.Customer;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.batch.item.file.FlatFileItemReader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerItemReader extends FlatFileItemReader<Customer> {

    public CustomerItemReader(Resource input) {
        super();

        setResource(input);
        setLinesToSkip(1); // Skip the header line

        setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id", "name", "birthday", "transaction"); // Set the column names
            }});
            setFieldSetMapper((FieldSet fieldSet) -> { // Map each line to a Customer object
                Customer customer = new Customer();
                customer.setId(fieldSet.readInt("id"));
                customer.setName(fieldSet.readString("name"));
                customer.setTransactions(fieldSet.readInt("transaction"));
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthday = dateFormat.parse(fieldSet.readString("birthday"));
                    customer.setBirthday(birthday);
                } catch (ParseException | java.text.ParseException e) {
                    // Handle parsing exception
                    e.printStackTrace(); // Or log the error
                }
                return customer;
            });
        }});
    }
}

