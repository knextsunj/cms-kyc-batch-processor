package com.github.knextsunj.cmskycbatchprocessor.tasklet

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.knextsunj.cmskycbatchprocessor.dto.AddressDTO
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

open class AddressTasklet: Tasklet {

    companion object {
        val log: Logger = LoggerFactory.getLogger(AddressTasklet::class.java)
    }

    @Autowired
    @Qualifier("addressProcessingServiceImpl")
    open lateinit var genericProcessingService: GenericProcessingService<AddressDTO?>

    @Autowired
    open lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Autowired
    open lateinit var environment: Environment

    @Autowired
    open lateinit var objectMapper: ObjectMapper

    override fun execute(stepContribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        var addressIdStr:String? = chunkContext.stepContext.jobParameters.get("addressId") as String?
        if(addressIdStr!=null) {
            val addrPair: Pair<String, String> =
                objectMapper.readValue(addressIdStr, Pair::class.java) as Pair<String, String>
            var addressId: Long? = addrPair.left?.toLong()
            val addressDTO = genericProcessingService.getData(addressId)

//        var objectWriter: ObjectWriter = ObjectMapper().writer().withDefaultPrettyPrinter();


            val addrDataPair = Pair.of(addressDTO, addrPair.right)
            var addressData = objectMapper.writer().writeValueAsString(addrDataPair)
            val topicName = environment.getProperty("cms.kycbatch.address.topic")

            try {

                kafkaTemplate.send(topicName, addressData)
            } catch (kafkaEx: KafkaException) {
                val errorMsg = kafkaEx.message
                CustomerTasklet.log.error("Failed to send address information for KYC", kafkaEx)
                throw ProcessingException("Failed to send address information for KYC.Actual error is:${errorMsg}")
            }

        }

        return RepeatStatus.FINISHED
    }
}