package org.example.demospringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.step.CustomerTasklet;
import org.example.demospringbatch.step.Step1Configuration;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;


@Slf4j
@Configuration
public class CustomerReportJobConfig {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job myJob(Step1Configuration stepA) {
        return new JobBuilder("myJob", jobRepository)
                .start(stepA.step1())
                .on(ExitStatus.FAILED.getExitCode()).end()
                .on(ExitStatus.COMPLETED.getExitCode()).to(step2()).end()
                .build();
    }


    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository).tasklet(tasklet(), transactionManager).
                allowStartIfComplete(true)
                .build();
    }


    @Bean
    public Tasklet tasklet() {
        return new CustomerTasklet();
    }
    @Bean
    public ExecutionContext executionContext() {
        return new ExecutionContext();
    }
}