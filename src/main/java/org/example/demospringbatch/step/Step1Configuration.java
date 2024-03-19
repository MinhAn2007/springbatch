package org.example.demospringbatch.step;

import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.batch.CustomerItemReader;
import org.example.demospringbatch.batch.CustomerItemWriter;
import org.example.demospringbatch.batch.process.BirthdayFilterProcessor;
import org.example.demospringbatch.batch.process.CompositeProcessor;
import org.example.demospringbatch.batch.process.TransactionValidatingProcessor;
import org.example.demospringbatch.models.Customer;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
public class Step1Configuration {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;
    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Customer, Customer>chunk(2,transactionManager)
                .reader(customerItemReader())
                .processor(compositeProcessor())
                .writer(writer())
                .faultTolerant()
                .skipLimit(10)
                .retryLimit(3)
                .noSkip(Exception.class)
                .noRetry(Exception.class)
                .listener(stepExecutionListener())
                .build();
    }

    @Bean
    public ItemReader<Customer> customerItemReader() {
        return new CustomerItemReader(new ClassPathResource("data.csv"));
    }
    @StepScope
    @Bean
    public ItemProcessor<Customer, Customer> compositeProcessor(){
        CompositeProcessor processor = new CompositeProcessor();
        processor.setDelegates(Arrays.asList(
                new BirthdayFilterProcessor(),
                new TransactionValidatingProcessor(3)
        ));

        return processor;
    }
    @StepScope
    @Bean
    public CustomerItemWriter writer() {
        return new CustomerItemWriter();
    }
    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("Step execution");
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                ExecutionContext executionContext = stepExecution
                        .getJobExecution()
                        .getExecutionContext();
                executionContext.put("items", writer().getCustomers());
                if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info("Step failed");
                    return ExitStatus.COMPLETED;
                } else {
                    return ExitStatus.FAILED;
                }
            }
        };
}}
