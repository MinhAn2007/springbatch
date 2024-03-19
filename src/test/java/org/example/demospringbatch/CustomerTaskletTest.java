package org.example.demospringbatch;

import org.example.demospringbatch.models.Customer;
import org.example.demospringbatch.step.tasklet.CustomerTasklet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.item.ExecutionContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

public class CustomerTaskletTest {
    @Mock
    private StepContext stepContext;
    @Mock
    private StepContribution stepContribution;

    @Mock
    private StepExecution stepExecution;

    @Mock
    private ChunkContext chunkContext;
    @Mock
    private CustomerTasklet customerTasklet;
    @Mock
    private org.springframework.batch.core.JobExecution jobExecution;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(chunkContext.getStepContext()).thenReturn(stepContext);
        when(stepContext.getStepExecution()).thenReturn(stepExecution);
        when(stepExecution.getJobExecution()).thenReturn(jobExecution);
        customerTasklet = new CustomerTasklet();
    }
    @Test
    public void shouldProcessCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setName("name");
        Date date = new Date(90,2 , 2);
        customer1.setBirthday(date);
        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setName("name");
        customer2.setBirthday(date);
        List<Customer> customers = Arrays.asList(customer1, customer2);
        ExecutionContext executionContext = new ExecutionContext();
        executionContext.put("items", customers);
        when(chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext()).thenReturn(executionContext);
        customerTasklet.execute(stepContribution, chunkContext);
    }
}
