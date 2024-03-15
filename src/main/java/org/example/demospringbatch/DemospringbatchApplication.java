package org.example.demospringbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class DemospringbatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemospringbatchApplication.class, args);
	}

}
