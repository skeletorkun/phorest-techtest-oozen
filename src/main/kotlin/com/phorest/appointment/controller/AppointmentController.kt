package com.phorest.appointment.controller

import com.phorest.appointment.domain.Appointment
import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.service.AppointmentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/appointments")
private class AppointmentController(val appointmentService: AppointmentService) {

    @GetMapping("/{id}")
    fun retrieveAppointment(@PathVariable("id") id: Long): Appointment {
        return appointmentService.retrieveAppointment(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addAppointment(@RequestBody appointmentDto: AppointmentDto): AppointmentDto {
        return appointmentService.addAppointment(appointmentDto);
    }

}