package com.phorest.appointment.service

import com.phorest.appointment.dto.Appointment
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class AppointmentServiceImpl : AppointmentService {

    private val logger = KotlinLogging.logger {}

    override fun retrieveAppointment(id: Long): Appointment {
        logger.debug { "retrieving appointment id $id" }
        return Appointment("123", "Something")
    }
}