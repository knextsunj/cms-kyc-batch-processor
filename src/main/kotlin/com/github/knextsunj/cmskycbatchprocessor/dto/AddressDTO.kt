package com.github.knextsunj.cmskycbatchprocessor.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class AddressDTO(@JsonProperty("id") val id:Long?,
                      @JsonProperty("deleted")  val deleted:String?,
                      @JsonProperty("street")  val street:String?, @JsonProperty("locality") val locality:String?,
                      @JsonProperty("area")  val area:String?,
                      @JsonProperty("pincode")  val pincode:Long?, @JsonProperty("city") val city:String?,
                      @JsonProperty("state") val state:String?,
                      @JsonProperty("country")  val country:String?, @JsonProperty("addressType") val addressType:String?,
                      @JsonProperty("customerId") val customerId:Long?): Serializable
