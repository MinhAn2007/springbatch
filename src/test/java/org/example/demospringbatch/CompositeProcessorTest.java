package org.example.demospringbatch;

import org.example.demospringbatch.models.Customer;
import org.example.demospringbatch.step.chunk.batch.process.CompositeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompositeProcessorTest {
    @Mock
    private ItemProcessor<Customer, Customer> delegate1;

    @Mock
    private ItemProcessor<Customer, Customer> delegate2;

    private CompositeProcessor compositeProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        compositeProcessor = new CompositeProcessor();
        compositeProcessor.setDelegates(List.of(delegate1, delegate2));
    }

    @Test
    public void shouldProcessCustomerThroughAllDelegates() throws Exception {
        Customer initialCustomer = new Customer();
        Customer afterDelegate1 = new Customer();
        Customer afterDelegate2 = new Customer();
        when(delegate1.process(initialCustomer)).thenReturn(afterDelegate1);
        when(delegate2.process(afterDelegate1)).thenReturn(afterDelegate2);
        Customer result = compositeProcessor.process(initialCustomer);
        System.out.println(afterDelegate2.getName());
        System.out.println(result.getName());
        assertEquals(afterDelegate2, result);
    }}
