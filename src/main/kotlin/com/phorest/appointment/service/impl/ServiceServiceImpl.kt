package com.phorest.appointment.service.impl

import com.phorest.appointment.dto.ServiceDto
import com.phorest.appointment.repository.AppointmentRepository
import com.phorest.appointment.repository.ServiceRepository
import com.phorest.appointment.service.ServiceService
import org.springframework.stereotype.Service

@Service
class ServiceServiceImpl(
    val serviceRepository: ServiceRepository,
    val appointmentRepository: AppointmentRepository,
) : ServiceService {
    override fun saveServices(serviceDtoList: List<ServiceDto>): List<ServiceDto> {

        val entities = serviceDtoList.map {
            com.phorest.appointment.domain.Service(
                it.id,
                it.name,
                it.price,
                it.loyaltyPoints,
                it.appointmentId?.let { id -> appointmentRepository.findById(id).orElseThrow() }
            )
        }

        val savedEntities = serviceRepository.saveAll(entities)

        return savedEntities.map {
            ServiceDto(
                it.id,
                it.name,
                it.price,
                it.loyaltyPoints,
                it.appointment?.id
            )
        }
    }
}