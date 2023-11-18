package com.github.knextsunj.cmskycbatchprocessor.messaging.impl

import com.github.knextsunj.cmskycbatchprocessor.messaging.JmsConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import org.springframework.context.annotation.Lazy;

@Service
@Qualifier("addressJmsConsumer")
open class AddressJmsConsumer : JmsConsumer {

    private val logger: Logger = LoggerFactory.getLogger(CustomerJmsConsumer::class.java)

    @Autowired
    @Lazy
    open lateinit var jobLauncher: JobLauncher

    @Autowired
    @Lazy
    open lateinit var addressJob: Job

    @JmsListener(destination = "address-id-kyc")
    override fun receiveMessage(message: String?) {
        if (message!=null) {
            var jobParameters: JobParameters = JobParametersBuilder().addString("addressId", message).toJobParameters();
            var jobExecution: JobExecution = jobLauncher.run(addressJob, jobParameters)
            if (jobExecution.exitStatus.equals(ExitStatus.COMPLETED)) {
                logger.info("Address job successfully completed")
            }
        }
    }
}