package org.example.demospringbatch;

import org.example.demospringbatch.step.tasklet.StepTaskletConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class StepTaskletConfigTest {
    @Mock
    private StepExecution stepExecution;

    @InjectMocks
    private StepTaskletConfig stepTaskletConfig;
    @Mock
    private StepContext stepContext;
    @Mock
    private org.springframework.batch.core.JobExecution jobExecution;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(stepContext.getStepExecution()).thenReturn(stepExecution);
        when(stepExecution.getJobExecution()).thenReturn(jobExecution);
    }

    @Test
    public void shouldCreateStep() {
        Step step = stepTaskletConfig.step2();
        assertNotNull(step);
    }

    @Test
    public void shouldHandleStepExecution() {
        when(stepExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        ExitStatus exitStatus = stepTaskletConfig.stepExecutionListener().afterStep(stepExecution);
        assertEquals(ExitStatus.COMPLETED, exitStatus);
    }
}