package com.phorest.appointment.service.impl

import com.phorest.appointment.domain.Appointment
import com.phorest.appointment.domain.toDto
import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.repository.AppointmentRepository
import com.phorest.appointment.repository.ClientRepository
import com.phorest.appointment.service.AppointmentService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.util.*

@Service
class AppointmentServiceImpl(
    val appointmentRepository: AppointmentRepository,
    val clientRepository: ClientRepository
) : AppointmentService {

    private val logger = KotlinLogging.logger {}
    override fun retrieveAppointments(): List<AppointmentDto> {
        logger.debug { "retrieving appointments" }
        val appointment = appointmentRepository.findAll()
        return appointment.map { it.toDto() }
    }

    override fun retrieveAppointment(id: UUID): AppointmentDto {
        logger.debug { "retrieving appointment id $id" }
        val appointment = appointmentRepository.findById(id).orElseThrow()
        return appointment.toDto()
    }

    override fun addAppointment(appointmentDto: AppointmentDto): AppointmentDto {
        logger.debug { "Adding appointment $appointmentDto" }
        val appointmentEntity = getAppointmentEntity(appointmentDto)

        val savedEntity = appointmentRepository.save(appointmentEntity)
        logger.debug { "Saved appointment $appointmentEntity" }

        return savedEntity.toDto()
    }

    override fun saveAppointments(appointmentDtoList: List<AppointmentDto>): List<AppointmentDto> {
        val entities = appointmentDtoList.map {
            getAppointmentEntity(it)
        }
        val savedAppointments = appointmentRepository.saveAll(entities)

        return savedAppointments.map {
            it.toDto()
        }
    }

    private fun getAppointmentEntity(appointmentDto: AppointmentDto) = Appointment(
        appointmentDto.id,
        appointmentDto.startTime,
        appointmentDto.endTime,
        appointmentDto.clientId?.let { clientRepository.findById(it).orElseThrow() }
    )
}



