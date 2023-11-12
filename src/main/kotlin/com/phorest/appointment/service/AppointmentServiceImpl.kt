package com.phorest.appointment.service

import com.phorest.appointment.domain.Appointment
import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.repository.AppointmentRepository
import com.phorest.appointment.util.DateUtils.Companion.parseOffsetDateTime
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.util.*

@Service
class AppointmentServiceImpl(val appointmentRepository: AppointmentRepository) : AppointmentService {

    private val logger = KotlinLogging.logger {}

    override fun retrieveAppointment(id: Long): Appointment {
        logger.debug { "retrieving appointment id $id" }
        return Appointment(
            UUID.randomUUID(),
            UUID.randomUUID(),
            parseOffsetDateTime("2018-05-31 14:30:00 +0100"),
            parseOffsetDateTime("2018-05-31 14:40:00 +0100"),
        )
    }

    override fun addAppointment(appointmentDto: AppointmentDto): AppointmentDto {
        logger.debug { "Adding appointment $appointmentDto" }
        val appointmentEntity = appointmentDto.let {
            Appointment(null, appointmentDto.clientId, appointmentDto.startTime, appointmentDto.endTime)
        }

        appointmentRepository.save(appointmentEntity)
        logger.debug { "Saved appointment $appointmentEntity" }

        return appointmentEntity.let {
            AppointmentDto(appointmentEntity.id, appointmentEntity.clientId, appointmentEntity.startTime, appointmentEntity.endTime)
        }
    }
}