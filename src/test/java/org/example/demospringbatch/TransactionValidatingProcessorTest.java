package org.example.demospringbatch;

import org.example.demospringbatch.step.chunk.batch.process.TransactionValidatingProcessor;
import org.example.demospringbatch.models.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionValidatingProcessorTest {

    @Test
    public void shouldPassValidationWhenTransactionsAreAboveLimit() {
        TransactionValidatingProcessor processor = new TransactionValidatingProcessor(5);
        Customer customer = new Customer();
        customer.setTransactions(6);
        Assertions.assertDoesNotThrow(() -> processor.process(customer));
    }

//    @Test
//    public void shouldThrowExceptionWhenTransactionsAreBelowLimit() {
//        TransactionValidatingProcessor processor = new TransactionValidatingProcessor(5);
//        Customer customer = new Customer();
//        customer.setTransactions(2);
//        Assertions.assertNull(processor.process(customer));
//    }

    @Test
    public void shouldPassValidationWhenTransactionsAreEqualToLimit() {
        TransactionValidatingProcessor processor = new TransactionValidatingProcessor(5);
        Customer customer = new Customer();
        customer.setTransactions(5);
        Assertions.assertDoesNotThrow(() -> processor.process(customer));
    }
}