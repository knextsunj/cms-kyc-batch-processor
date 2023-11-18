package com.github.knextsunj.cmskycbatchprocessor.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.io.Serializable
import java.time.LocalDate

data class CustomerDTO(
    @field:JsonProperty("id") @param:JsonProperty("id") val id: Long, @field:JsonProperty(
        "name"
    ) @param:JsonProperty("name") val name: String, @field:JsonProperty(
        "deleted"
    ) @param:JsonProperty("deleted") val deleted: String?, @field:JsonProperty(
        "dob"
    ) @field:JsonDeserialize(using = LocalDateDeserializer::class) @field:JsonFormat(
        pattern = "dd/MM/yyyy"
    ) @field:JsonSerialize(using = LocalDateSerializer::class) @param:JsonSerialize(
        using = LocalDateSerializer::class
    ) @param:JsonFormat(pattern = "dd/MM/yyyy") @param:JsonDeserialize(
        using = LocalDateDeserializer::class
    ) @param:JsonProperty("dob") val dob: LocalDate, @field:JsonProperty(
        "gender"
    ) @param:JsonProperty("gender") val gender: String, @field:JsonProperty(
        "mobileNo"
    ) @param:JsonProperty("mobileNo") val mobileNo: Long, @field:JsonProperty(
        "emailAddress"
    ) @param:JsonProperty("emailAddress") val emailAddress: String, @field:JsonProperty(
        "customerStatusDescr"
    ) @param:JsonProperty("customerStatusDescr") val customerStatusDescr: String
): Serializable
