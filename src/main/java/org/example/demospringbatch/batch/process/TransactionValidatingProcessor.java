package org.example.demospringbatch.batch.process;

import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.models.Customer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
@Slf4j
public class TransactionValidatingProcessor extends ValidatingItemProcessor<Customer> {

    public TransactionValidatingProcessor(final int limit) {
        super(
                item -> {
                    if (item.getTransactions() < limit) {
                        log.info("Customer "+ item.getName()+" has less transactions", item.getTransactions());
                    }
                }
        );
        setFilter(true);
    }
}