package org.example.demospringbatch.step.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.models.Customer;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;

import javax.sound.sampled.Line;
import java.util.List;
@Slf4j
@Configuration
public class CustomerTasklet implements Tasklet, StepExecutionListener {
    private List<Line> lines;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.lines = (List<Line>) executionContext.get("lines");
        log.info("Lines Processor initialized.");
    }

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
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Lines Processor ended.");
        return ExitStatus.COMPLETED;
    }
}

