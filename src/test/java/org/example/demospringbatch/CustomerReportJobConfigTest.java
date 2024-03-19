package org.example.demospringbatch;

import org.example.demospringbatch.config.CustomerReportJobConfig;
import org.example.demospringbatch.step.chunk.Step1Configuration;
import org.example.demospringbatch.step.tasklet.StepTaskletConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerReportJobConfigTest {
    @Mock
    private JobRepository jobRepository;
    @Mock
    private Step1Configuration step1;
    @Mock
    private StepTaskletConfig step2;
    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job job;

    @InjectMocks
    private CustomerReportJobConfig config;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(step1.step1()).thenReturn(mock(Step.class));
        when(step2.step2()).thenReturn(mock(Step.class));
        when(jobRepository.getLastJobExecution(anyString(), any(JobParameters.class))).thenReturn(mock(JobExecution.class));

    }

    @Test
    @DisplayName("Should successfully run job when all steps complete successfully")
    public void shouldSuccessfullyRunJobWhenAllStepsCompleteSuccessfully() throws Exception {
        JobExecution jobExecution = new JobExecution(1L);
        jobExecution.setExitStatus(ExitStatus.COMPLETED);
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(jobExecution);
        JobExecution result = jobLauncher.run(config.myJob(), new JobParameters());
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
        assertEquals(ExitStatus.COMPLETED, result.getExitStatus());
    }

    @Test
    @DisplayName("Should fail to run job when any step fails")
    public void shouldFailToRunJobWhenAnyStepFails() throws Exception {
        JobExecution jobExecution = new JobExecution(1L);
        jobExecution.setExitStatus(ExitStatus.FAILED);
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(jobExecution);
        JobExecution result = jobLauncher.run(config.myJob(), new JobParameters());
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
        assertEquals(ExitStatus.FAILED, result.getExitStatus());
    }
}