package com.github.knextsunj.cmskycbatchprocessor.service.impl

import com.github.knextsunj.cmskycbatchprocessor.dao.GenericProcessingDAO
import com.github.knextsunj.cmskycbatchprocessor.dto.AddressDTO
import com.github.knextsunj.cmskycbatchprocessor.service.GenericProcessingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
@Qualifier("addressProcessingServiceImpl")
open class AddressProcessingServiceImpl: GenericProcessingService<AddressDTO?> {

    @Autowired
    @Qualifier("addressProcessingDAOImpl")
    open lateinit var genericProcessingDAO: GenericProcessingDAO<AddressDTO?>


    override fun getData(id: Long?): AddressDTO? {
        return genericProcessingDAO.getData(id)
    }
}