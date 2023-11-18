package com.github.knextsunj.cmskycbatchprocessor.config;

import com.github.knextsunj.cmskycbatchprocessor.tasklet.AddressTasklet;
import com.github.knextsunj.cmskycbatchprocessor.tasklet.CustomerTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BatchConfiguration {

    @Autowired
    @Lazy
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    @Lazy
    private StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private PlatformTransactionManager platformTransactionManager;

    @Bean
    @Lazy
    public Step customerJobStep() {
        return this.stepBuilderFactory.get("customerStep1")
                .tasklet(customerTasklet()).build();
    }

    @Bean
    @Lazy
    public Tasklet addressTasklet() {
        return new AddressTasklet();
    }

    @Bean
    @Lazy
    public Job addressJob() {
        return this.jobBuilderFactory.get("addressJob")
                .start(addressJobStep())
                .build();
    }

    @Bean
    @Lazy
    public Step addressJobStep() {
        return this.stepBuilderFactory.get("addressStep1")
                .tasklet(addressTasklet()).build();
    }

    @Bean
    @Lazy
    public Tasklet customerTasklet() {
        return new CustomerTasklet();
    }

    @Bean
    @Lazy
    public Job customerJob() {
        return this.jobBuilderFactory.get("customerJob")
                .start(customerJobStep())
                .build();
    }

//    @Bean
//    public JobRepositoryFactoryBean jobRepository() {
//        JobRepositoryFactoryBean jobRepositoryFactoryBean =  new JobRepositoryFactoryBean();
//        jobRepositoryFactoryBean.setDatabaseType("ORACLE");
//        jobRepositoryFactoryBean.setDataSource(dataSource);
//        jobRepositoryFactoryBean.setTransactionManager(platformTransactionManager);
//        jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
//        return jobRepositoryFactoryBean;
//    }
}
