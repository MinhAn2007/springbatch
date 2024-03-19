package org.example.demospringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.example.demospringbatch.step.tasklet.CustomerTasklet;
import org.example.demospringbatch.step.chunk.Step1Configuration;
import org.example.demospringbatch.step.tasklet.StepTaskletConfig;
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


@Slf4j
@Configuration
public class CustomerReportJobConfig {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job myJob(Step1Configuration stepA, StepTaskletConfig stepB) {
        return new JobBuilder("myJob", jobRepository)
                .start(stepA.step1())
                .on(ExitStatus.FAILED.getExitCode()).end()
                .on(ExitStatus.COMPLETED.getExitCode()).to(stepB.step2()).end()
                .build();
    }

    @Bean
    public ExecutionContext executionContext() {
        return new ExecutionContext();
    }
}