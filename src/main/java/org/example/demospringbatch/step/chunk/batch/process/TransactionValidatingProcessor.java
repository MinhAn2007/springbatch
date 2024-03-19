package org.example.demospringbatch.step.chunk.batch.process;
import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.models.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

@Slf4j
public class TransactionValidatingProcessor implements ItemProcessor<Customer, Customer> {
    private final int limit;

    public TransactionValidatingProcessor(int limit) {
        this.limit = limit;
    }

    @Override
    public Customer process(Customer item) throws Exception {
        // Handle null item gracefully
        if (item == null) {
            return null;
        }

        log.info("TransactionValidatingProcessor");
        log.info("Transactions: {}", item.getTransactions());
        log.info("Limit: {}", limit);

        // Check if item meets the requirement
        if (item.getTransactions() < limit) {
            // Log a message indicating the item is filtered out
            log.info("Item filtered out: Transaction count is below limit!");
            // Return null to indicate the item should be filtered out
            return null;
        }

        // Return the item if it meets the requirement
        return item;
    }
}



