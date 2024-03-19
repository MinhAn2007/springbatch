package org.example.demospringbatch;

import org.example.demospringbatch.step.chunk.batch.process.BirthdayFilterProcessor;
import org.example.demospringbatch.models.Customer;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {DemospringbatchApplication.class, BatchTestConfiguration.class})
public class BirthdayFilterProcessorTest {
    @Autowired
    private BirthdayFilterProcessor processor;

    public StepExecution getStepExecution() {
        return MetaDataInstanceFactory.createStepExecution();
    }

    @Test
    @org.junit.jupiter.api.DisplayName("filter")
    public void filter() throws Exception {
        final Customer customer = new Customer();
        customer.setId(1);
        customer.setName("name");
        Date date = new Date(90,2 , 2);
        customer.setBirthday(date);

        System.out.println(customer.getBirthday() + " " + customer.getName() + " " + customer.getId());
        Assertions.assertNotNull(processor.process(customer));
    }
    @Test
    public void filterId() throws Exception {
        final Customer customer = new Customer();
        customer.setId(1);
        customer.setName("name");
        Date date = new Date(90,2 , 2);
        customer.setBirthday(date);
        final int id = StepScopeTestUtils.doInStepScope(
                getStepExecution(),
                () -> processor.process(customer).getId()
        );
        Assert.assertEquals(1, id);
    }
}
