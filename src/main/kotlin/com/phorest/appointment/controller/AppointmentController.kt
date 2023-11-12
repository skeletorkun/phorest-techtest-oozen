package com.phorest.appointment.controller

import com.phorest.appointment.dto.Appointment
import com.phorest.appointment.service.AppointmentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/appointments")
private class AppointmentController(val appointmentService: AppointmentService) {

    @GetMapping("/{id}")
    fun retrieveAppointment(@PathVariable("id") id: Long): Appointment {
        return appointmentService.retrieveAppointment(id);
    }
}