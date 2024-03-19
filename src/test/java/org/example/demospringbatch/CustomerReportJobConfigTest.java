package org.example.demospringbatch;

import jdk.jfr.Name;
import org.example.demospringbatch.BatchTestConfiguration;
import org.example.demospringbatch.DemospringbatchApplication;
import org.example.demospringbatch.config.CustomerReportJobConfig;
import org.example.demospringbatch.step.Step1Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {DemospringbatchApplication.class, BatchTestConfiguration.class})
public class CustomerReportJobConfigTest {
    @SpyBean
    private Step1Configuration step;
    @Autowired
    private JobLauncherTestUtils testUtils;

    @Autowired
    private CustomerReportJobConfig config;

    @Test
    @Name("testEntireJob")
    public void testEntireJob() throws Exception {
        final JobExecution result = testUtils.getJobLauncher().run(config.myJob(step), testUtils.getUniqueJobParameters());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }

    @Test
    @Name("testSpecificStep")
    public void testStepExecution() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        final JobExecution result = testUtils.getJobLauncher().run(config.myJob(step), testUtils.getUniqueJobParameters());
        Assertions.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }
}
