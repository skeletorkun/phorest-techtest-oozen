package com.phorest.appointment.service

import com.phorest.appointment.domain.Appointment
import com.phorest.appointment.dto.AppointmentDto

interface AppointmentService {
    fun retrieveAppointment(id: Long): Appointment
    fun addAppointment(appointmentDto: AppointmentDto): AppointmentDto
}