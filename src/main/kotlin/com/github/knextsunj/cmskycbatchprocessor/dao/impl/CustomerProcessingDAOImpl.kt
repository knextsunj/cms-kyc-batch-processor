package com.github.knextsunj.cmskycbatchprocessor.dao.impl

import com.github.knextsunj.cmskycbatchprocessor.dao.GenericProcessingDAO
import com.github.knextsunj.cmskycbatchprocessor.dto.CustomerDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import javax.annotation.PostConstruct
import javax.sql.DataSource

@Repository
@Qualifier("customerProcessingDAOImpl")
open class CustomerProcessingDAOImpl: GenericProcessingDAO<CustomerDTO?> {

    companion object {

        const val GET_CUSTOMER_QUERY =
            "select c.id,c.name ,c.dob,c.gender,c.mobile_no,c.email,c.is_deleted,cs.name as customer_status from customer c inner join customer_status cs on c.customer_status_id=cs.id\n" +
                    "where c.id=:id"
    }

    @Autowired
    open lateinit var dataSource: DataSource

    open lateinit var namedParameterJbdcTemplate: NamedParameterJdbcTemplate;

    private val customerDTOMapper = RowMapper<CustomerDTO> { rs: ResultSet, rowNum: Int ->

        CustomerDTO(rs.getLong("id"),rs.getString("name"),rs.getString("is_deleted"),rs.getDate("dob").toLocalDate(),rs.getString("gender"),
         rs.getLong("mobile_no"),rs.getString("email"),rs.getString("customer_status"))

    }

    override fun getData(id: Long?): CustomerDTO? {
        val namedParameters = HashMap<String,Long?>()
        namedParameters.put("id",id)
        return  namedParameterJbdcTemplate.queryForObject(GET_CUSTOMER_QUERY,namedParameters,customerDTOMapper);
   }

    @PostConstruct
    fun init() {
        namedParameterJbdcTemplate = NamedParameterJdbcTemplate(dataSource)
    }
}
