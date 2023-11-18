package com.github.knextsunj.cmskycbatchprocessor;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.jersey.JerseyServerMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = JerseyServerMetricsAutoConfiguration.class)
@EnableBatchProcessing
@EnableTransactionManagement
public class CmsKycBatchProcessorApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CmsKycBatchProcessorApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CmsKycBatchProcessorApplication.class);
	}


}
