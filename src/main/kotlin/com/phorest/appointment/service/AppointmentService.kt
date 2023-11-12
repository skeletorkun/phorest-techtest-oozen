package com.phorest.appointment.service

import com.phorest.appointment.dto.Appointment

fun interface AppointmentService {
    fun retrieveAppointment(id: Long): Appointment;
}