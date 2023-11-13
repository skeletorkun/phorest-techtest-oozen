package com.phorest.appointment.service.impl

import com.phorest.appointment.domain.Appointment
import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.repository.AppointmentRepository
import com.phorest.appointment.repository.ClientRepository
import com.phorest.appointment.service.AppointmentService
import com.phorest.appointment.util.DateUtils.Companion.parseOffsetDateTime
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.util.*

@Service
class AppointmentServiceImpl(
    val appointmentRepository: AppointmentRepository,
    val clientRepository: ClientRepository
) : AppointmentService {

    private val logger = KotlinLogging.logger {}

    override fun retrieveAppointment(id: Long): Appointment {
        logger.debug { "retrieving appointment id $id" }
        return Appointment(
            UUID.randomUUID(),
            parseOffsetDateTime("2018-05-31 14:30:00 +0100"),
            parseOffsetDateTime("2018-05-31 14:40:00 +0100"),
        )
    }

    override fun addAppointment(appointmentDto: AppointmentDto): AppointmentDto {
        logger.debug { "Adding appointment $appointmentDto" }
        val appointmentEntity = Appointment(UUID.randomUUID(), appointmentDto.startTime, appointmentDto.endTime)

        appointmentRepository.save(appointmentEntity)
        logger.debug { "Saved appointment $appointmentEntity" }

        return AppointmentDto(
            appointmentEntity.id,
            appointmentEntity.startTime,
            appointmentEntity.endTime,
            appointmentEntity.client!!.id
        ) // TODO remove or fix
    }

    override fun saveAppointments(appointmentDtoList: List<AppointmentDto>): List<AppointmentDto> {
        val entities = appointmentDtoList.map {
            Appointment(
                it.id,
                it.startTime,
                it.endTime,
                it.clientId?.let { it1 -> clientRepository.findById(it1).orElseThrow() }
            )
        }
        val savedAppointments = appointmentRepository.saveAll(entities)

        return savedAppointments.map {
            AppointmentDto(
                it.id,
                it.startTime,
                it.endTime,
                it.client?.id,
            )
        }
    }
}