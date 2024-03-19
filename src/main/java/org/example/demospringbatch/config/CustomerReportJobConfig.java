package org.example.demospringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.batch.CustomerItemReader;
import org.example.demospringbatch.batch.CustomerItemWriter;
import org.example.demospringbatch.batch.process.BirthdayFilterProcessor;
import org.example.demospringbatch.batch.process.CompositeProcessor;
import org.example.demospringbatch.batch.process.TransactionValidatingProcessor;
import org.example.demospringbatch.models.Customer;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Configuration
public class CustomerReportJobConfig implements JobLauncher{

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job myJob() {
        return new JobBuilder("myJob", jobRepository)
                .start(step1()).next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository).<Customer,Customer>chunk(2,transactionManager)
                .reader(customerItemReader()).processor(compositeProcessor()).writer(writer()).
                allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository).tasklet(tasklet(),transactionManager).
                allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<Customer> customerItemReader() {
        return new CustomerItemReader(new ClassPathResource("data.csv"));
    }
    @StepScope
    @Bean
    public ItemProcessor<Customer, Customer> compositeProcessor() {
        CompositeProcessor processor = new CompositeProcessor();
        processor.setDelegates(Arrays.asList(
                new BirthdayFilterProcessor(),
                new TransactionValidatingProcessor(7)
        ));

        return processor;
    }
    @StepScope
    @Bean
    public CustomerItemWriter writer() {
        return new CustomerItemWriter();
    }

    @Bean
    public Tasklet tasklet() {
        return new CustomerTasklet();
    }

    @Override
    public JobExecution run(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        return null;
    }
}