package org.example.demospringbatch.step.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
@Configuration
@Slf4j
public class StepTaskletConfig {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private CustomerTasklet tasklet;
    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository).tasklet(tasklet, transactionManager).
                allowStartIfComplete(true)
                .listener(stepExecutionListener())
                .build();
    }
    @ConditionalOnMissingBean(name = "stepExecutionListener")
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("Step tasklet is about to start");
            }
            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
                System.out.println("executionContext = " + executionContext);
                if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info("Step tasklet has completed successfully");
                    return ExitStatus.COMPLETED;
                } else {
                    log.info("Step execution has failed");
                    return ExitStatus.FAILED;
                }
            }
        };
    }
}
