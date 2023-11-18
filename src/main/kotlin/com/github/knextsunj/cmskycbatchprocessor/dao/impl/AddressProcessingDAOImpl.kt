package com.github.knextsunj.cmskycbatchprocessor.dao.impl

import com.github.knextsunj.cmskycbatchprocessor.dao.GenericProcessingDAO
import com.github.knextsunj.cmskycbatchprocessor.dto.AddressDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import javax.annotation.PostConstruct
import javax.sql.DataSource

@Repository
@Qualifier("addressProcessingDAOImpl")
open class AddressProcessingDAOImpl: GenericProcessingDAO<AddressDTO?> {

    companion object {

        const val GET_ADDRESS_QUERY = "select cust.id as customer_uid,addr.id as id, addr.STREET,addr.LOCALITY,addr.AREA,addr.PINCODE,addrType.name as address_type,cust.name as customer,cy.name as city,st.name as state,ct.name as country,addr.IS_DELETED from cms.address addr inner join \n" +
                "cms.address_type addrType on addr.address_type_id=addrType.id inner join cms.customer cust on addr.customer_id=cust.id inner join \n" +
                "cms.city cy on addr.city_id=cy.id inner join cms.state st on addr.state_id=st.id inner join cms.country ct on addr.country_id=ct.id where addr.id=:id";

    }

    @Autowired
    open lateinit var dataSource: DataSource

    open lateinit var namedParameterJbdcTemplate: NamedParameterJdbcTemplate;

    private val addressDTOMapper = RowMapper<AddressDTO> { rs: ResultSet, rowNum: Int ->

        AddressDTO(rs.getLong("id"),rs.getString("is_deleted"),rs.getString("street"),rs.getString("locality"),rs.getString("area"),rs.getLong("pincode"),
        rs.getString("city"),rs.getString("state"),rs.getString("country"),rs.getString("address_type"),rs.getLong("customer_id"))

    }

    override fun getData(id: Long?): AddressDTO? {
        val namedParameters = HashMap<String,Long?>()
        namedParameters.put("id",id)
        return  namedParameterJbdcTemplate.queryForObject(AddressProcessingDAOImpl.GET_ADDRESS_QUERY,namedParameters,addressDTOMapper);
    }

    @PostConstruct
    fun init() {
        namedParameterJbdcTemplate = NamedParameterJdbcTemplate(dataSource)
    }
}