package org.example.demospringbatch.batch;

import org.example.demospringbatch.models.Customer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.*;
import java.util.List;

public class CustomerItemWriter implements ItemWriter<Customer>, Closeable {
    private final PrintWriter writer;

    public CustomerItemWriter() {
        OutputStream out;
        try {
            out = new FileOutputStream("output.txt");
        } catch (FileNotFoundException e) {
            out = System.out;
        }
        this.writer = new PrintWriter(out);
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        for (Customer item : chunk) {
            System.out.println("Writing item: " + item.getName());
            String formattedCustomer = String.format("ID: %d, Name: %s, Birthday: %s, Transaction: %d",
                    item.getId(), item.getName(), item.getBirthday(), item.getTransactions());

            writer.println(formattedCustomer);
        }
    }
}