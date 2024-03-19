package org.example.demospringbatch;

import jdk.jfr.Name;
import org.example.demospringbatch.config.CustomerReportJobConfig;
import org.example.demospringbatch.step.chunk.Step1Configuration;
import org.example.demospringbatch.step.tasklet.StepTaskletConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {DemospringbatchApplication.class, BatchTestConfiguration.class})
public class CustomerReportJobConfigTest {
    @Autowired
    private JobLauncherTestUtils testUtils;

    @Autowired
    private CustomerReportJobConfig config;

    @Test
    @Name("testEntireJob")
    public void testEntireJob() throws Exception {
        final JobExecution result = testUtils.getJobLauncher().run(config.myJob(), testUtils.getUniqueJobParameters());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }

    @Test
    @Name("testSpecificStep")
    public void testStepExecution() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        final JobExecution result = testUtils.getJobLauncher().run(config.myJob(), testUtils.getUniqueJobParameters());
        Assertions.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }
}
