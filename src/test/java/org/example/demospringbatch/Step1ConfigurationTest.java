package org.example.demospringbatch;

import org.example.demospringbatch.models.Customer;
import org.example.demospringbatch.step.chunk.Step1Configuration;
import org.example.demospringbatch.step.chunk.batch.CustomerItemReader;
import org.example.demospringbatch.step.chunk.batch.CustomerItemWriter;
import org.example.demospringbatch.step.chunk.batch.process.CompositeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class Step1ConfigurationTest {
    @Mock
    private JobRepository jobRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private StepExecution stepExecution;

    @InjectMocks
    private Step1Configuration step1Configuration;
    @Mock
    private JobExecution jobExecution;
    @Mock
    private ExecutionContext executionContext;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(stepExecution.getJobExecution()).thenReturn(jobExecution);
        when(jobExecution.getExecutionContext()).thenReturn(executionContext);

    }

    @Test
    public void shouldCreateStep() {
        Step step = step1Configuration.step1();
        assertEquals("step1", step.getName());
    }

    @Test
    public void shouldCreateCustomerItemReader() {
        ItemReader<Customer> reader = step1Configuration.customerItemReader();
        assertEquals(CustomerItemReader.class, reader.getClass());
    }

    @Test
    public void shouldCreateCompositeProcessor() {
        ItemProcessor<Customer, Customer> processor = step1Configuration.compositeProcessor();
        assertEquals(CompositeProcessor.class, processor.getClass());
    }

    @Test
    public void shouldCreateCustomerItemWriter() {
        CustomerItemWriter writer = step1Configuration.writer();
        assertEquals(CustomerItemWriter.class, writer.getClass());
    }

    @Test
    public void shouldHandleStepExecution() {
        when(stepExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        ExitStatus exitStatus = step1Configuration.stepExecutionListener().afterStep(stepExecution);
        assertEquals(ExitStatus.COMPLETED, exitStatus);
    }
}
