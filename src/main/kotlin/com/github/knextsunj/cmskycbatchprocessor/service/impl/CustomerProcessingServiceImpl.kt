package com.github.knextsunj.cmskycbatchprocessor.service.impl

import com.github.knextsunj.cmskycbatchprocessor.dao.GenericProcessingDAO
import com.github.knextsunj.cmskycbatchprocessor.dto.CustomerDTO
import com.github.knextsunj.cmskycbatchprocessor.service.GenericProcessingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
@Qualifier("customerProcessingServiceImpl")
open class CustomerProcessingServiceImpl :
    GenericProcessingService<CustomerDTO?> {

    @Autowired
    @Qualifier("customerProcessingDAOImpl")
    open lateinit var genericProcessingDAO: GenericProcessingDAO<CustomerDTO?>


    override fun getData(id: Long?): CustomerDTO? {
        return genericProcessingDAO.getData(id)
    }


}