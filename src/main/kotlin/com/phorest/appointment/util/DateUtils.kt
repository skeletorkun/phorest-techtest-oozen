package com.phorest.appointment.util

import com.phorest.appointment.util.Constants.Companion.PHOREST_OFFSET_DATE_TIME_FORMAT
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object {
        fun parseOffsetDateTime(dateTime: String): OffsetDateTime =
            OffsetDateTime.parse(dateTime, DateTimeFormatter.ofPattern(PHOREST_OFFSET_DATE_TIME_FORMAT))
    }
}