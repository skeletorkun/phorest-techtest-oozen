package com.phorest.appointment.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.phorest.appointment.util.Constants.Companion.PHOREST_OFFSET_DATE_TIME_FORMAT
import java.time.OffsetDateTime
import java.util.*

data class AppointmentDto(

    val id: UUID?,
    val clientId: UUID,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PHOREST_OFFSET_DATE_TIME_FORMAT)
    val startTime: OffsetDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PHOREST_OFFSET_DATE_TIME_FORMAT)
    val endTime: OffsetDateTime,
)