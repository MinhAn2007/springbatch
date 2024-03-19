package org.example.demospringbatch.step.chunk.batch.process;

import lombok.Setter;
import org.example.demospringbatch.models.Customer;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class CompositeProcessor implements ItemProcessor<Customer, Customer> {
    private StepExecution stepExecution;

    @Setter
    private List<ItemProcessor<Customer, Customer>> delegates;

    public CompositeProcessor() {
        this.delegates = new ArrayList<>();
    }

    @Override
    public Customer process(Customer item) throws Exception {
        Customer processedItem = item;
        for (ItemProcessor<Customer, Customer> delegate : delegates) {
            processedItem = delegate.process(processedItem);
        }
        return processedItem;
    }
}
