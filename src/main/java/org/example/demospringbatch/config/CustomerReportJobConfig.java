package org.example.demospringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.batch.CustomerItemReader;
import org.example.demospringbatch.batch.CustomerItemWriter;
import org.example.demospringbatch.batch.process.BirthdayFilterProcessor;
import org.example.demospringbatch.batch.process.TransactionValidatingProcessor;
import org.example.demospringbatch.models.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
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

@Slf4j
@Configuration
public class CustomerReportJobConfig {

    @Bean
    public Job myJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("myJob", jobRepository)
                .start(step)
                .build();
    }
    @Bean
    public Step myStep(JobRepository jobRepository, Tasklet myTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep", jobRepository).<Customer,Customer>chunk(7,transactionManager)
                        .reader(customerItemReader()).processor(processor()).writer(writer()).
                allowStartIfComplete(true)
                .build();
    }
    @Bean
    public ItemReader<Customer> customerItemReader() {
        return new CustomerItemReader(new ClassPathResource("data.csv"));
    }
    @StepScope
    @Bean
    public ItemProcessor<Customer, Customer> processor() {
        final CompositeItemProcessor<Customer, Customer> processor = new CompositeItemProcessor<>();
        processor.setDelegates(Arrays.asList(new BirthdayFilterProcessor(), new TransactionValidatingProcessor(5)));
        return processor;
    }
    @StepScope
    @Bean
    public CustomerItemWriter writer() {
        return new CustomerItemWriter();
    }
//    @Bean
//    public Step chunkStep() {
//        return StepBuilder.get("chunkStep")
//                .<Customer, Customer>chunk(20)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }

    @Bean
    public Tasklet tasklet() {
        return new CustomerTasklet();
    }
}