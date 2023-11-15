package com.phorest.appointment.service

import com.phorest.appointment.dto.AppointmentDto
import java.util.*

interface AppointmentService {
    fun retrieveAppointment(id: UUID): AppointmentDto
    fun addAppointment(appointmentDto: AppointmentDto): AppointmentDto
    fun saveAppointments(appointmentDtoList: List<AppointmentDto>): List<AppointmentDto>

}