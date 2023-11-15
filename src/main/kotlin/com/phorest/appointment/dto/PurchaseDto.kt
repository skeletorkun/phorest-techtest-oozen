package com.phorest.appointment.dto

import com.opencsv.bean.CsvBindByName
import java.util.*

data class PurchaseDto(

    @CsvBindByName(column = "id")
    val id: UUID = UUID.randomUUID(),

    @CsvBindByName(column = "name")
    val name: String? = "",
    @CsvBindByName(column = "price")
    val price: Double? = null,
    @CsvBindByName(column = "loyalty_points")
    val loyaltyPoints: Int? = null,

    @CsvBindByName(column = "appointment_id")
    val appointmentId: UUID? = null,
)