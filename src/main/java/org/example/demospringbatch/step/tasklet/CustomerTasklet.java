package org.example.demospringbatch.step.tasklet;

import org.example.demospringbatch.models.Customer;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerTasklet implements Tasklet {


   @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();
        List<Customer> myValue = (List<Customer>) jobContext.get("items");
        for (Customer customer : myValue) {
            System.out.println("Customer: " + customer.getName());
        }
        return RepeatStatus.FINISHED;
    }
}

