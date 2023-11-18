package com.github.knextsunj.cmskycbatchprocessor.tasklet

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.knextsunj.cmskycbatchprocessor.dto.CustomerDTO
import com.github.knextsunj.cmskycbatchprocessor.exception.ProcessingException
import com.github.knextsunj.cmskycbatchprocessor.service.GenericProcessingService
import org.apache.commons.lang3.tuple.Pair
import org.apache.kafka.common.KafkaException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.env.Environment
import org.springframework.kafka.core.KafkaTemplate
import org.apache.commons.lang3.tuple.Triple

open class CustomerTasklet:Tasklet {

    companion object {
        val log: Logger = LoggerFactory.getLogger(CustomerTasklet::class.java)
    }

    @Autowired
    @Qualifier("customerProcessingServiceImpl")
    open lateinit var genericProcessingService: GenericProcessingService<CustomerDTO?>

    @Autowired
    open lateinit var kafkaTemplate: KafkaTemplate<String,String>

    @Autowired
    open lateinit var environment: Environment

    @Autowired
    open lateinit var objectMapper: ObjectMapper

    override fun execute(stepContribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        var customerIdStr: String? = chunkContext.stepContext.jobParameters.get("customerId") as String?;
        log.info("CustomerTasklet:Receiving customer data from queue:{}",customerIdStr)
        if (customerIdStr != null) {
            val custPair: Pair<String, String> = objectMapper.readValue(
                customerIdStr,
                Pair::class.java
            ) as Pair<String, String>
            var customerId: Long? = custPair.left.toLong()

            val customerDTO = genericProcessingService.getData(customerId)

//            var objectWriter: ObjectWriter = ObjectMapper().writer().withDefaultPrettyPrinter();
            val custDataPair: Pair<CustomerDTO, String> = Pair.of(customerDTO, custPair.right)
            var customerData = objectMapper.writer().writeValueAsString(custDataPair)
            val topicName = environment.getProperty("cms.kycbatch.customer.topic")

            try {
                log.info("Writing to kafka customerdata:{}",customerData)
                kafkaTemplate.send(topicName, customerData)
            } catch (kafkaEx: KafkaException) {
                val errorMsg = kafkaEx.message
                log.error("Failed to send customer information for KYC", kafkaEx)
                throw ProcessingException("Failed to send customer information for KYC.Actual error is:${errorMsg}")
            }

        }
        return RepeatStatus.FINISHED
    }
}