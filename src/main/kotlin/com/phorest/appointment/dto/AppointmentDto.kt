package com.phorest.appointment.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvDate
import com.phorest.appointment.util.Constants.Companion.PHOREST_OFFSET_DATE_TIME_FORMAT
import java.time.OffsetDateTime
import java.util.*

data class AppointmentDto(

    @CsvBindByName(column = "id")
    val id: UUID = UUID.randomUUID(),

    @CsvBindByName(column = "start_time")
    @CsvDate(value = PHOREST_OFFSET_DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PHOREST_OFFSET_DATE_TIME_FORMAT)
    val startTime: OffsetDateTime? = null,

    @CsvBindByName(column = "end_time")
    @CsvDate(value = PHOREST_OFFSET_DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PHOREST_OFFSET_DATE_TIME_FORMAT)
    val endTime: OffsetDateTime? = null,

    @CsvBindByName(column = "client_id")
    val clientId: UUID? = null,

    val purchases: List<String>? = mutableListOf(),
    val services: List<String>? = mutableListOf(),
)