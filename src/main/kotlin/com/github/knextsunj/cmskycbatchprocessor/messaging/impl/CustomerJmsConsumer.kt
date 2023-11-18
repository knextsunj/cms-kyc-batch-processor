package com.github.knextsunj.cmskycbatchprocessor.messaging.impl

import com.github.knextsunj.cmskycbatchprocessor.messaging.JmsConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service

@Service
@Qualifier("customerJmsConsumer")
open class CustomerJmsConsumer : JmsConsumer {

    private val logger: Logger = LoggerFactory.getLogger(CustomerJmsConsumer::class.java)

    @Autowired
    @Lazy
    open lateinit var jobLauncher: JobLauncher

    @Autowired
    @Lazy
    open lateinit var customerJob: Job

    @JmsListener(destination = "customer-id-kyc")
    override fun receiveMessage(id: String?) {
        logger.info("Message from customer queue is:::: {}",id);
        if (id != null) {
            var jobParameters: JobParameters = JobParametersBuilder().addString("customerId", id).toJobParameters();
            var jobExecution: JobExecution = jobLauncher.run(customerJob, jobParameters)
            if (jobExecution.exitStatus.equals(ExitStatus.COMPLETED)) {
                logger.info("Customer job successfully completed")
            }
        }
    }
}