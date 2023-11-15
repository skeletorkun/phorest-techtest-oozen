package com.phorest.appointment.controller

import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.service.AppointmentService
import com.phorest.appointment.service.CsvService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/appointments")
private class AppointmentController(val appointmentService: AppointmentService, val csvService: CsvService) {

    private val logger = KotlinLogging.logger {}


    @GetMapping
    fun retrieveAppointments(): List<AppointmentDto> {
        return appointmentService.retrieveAppointments();
    }

    @GetMapping("/{id}")
    fun retrieveAppointment(@PathVariable("id") id: UUID): AppointmentDto {
        return appointmentService.retrieveAppointment(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addAppointment(@RequestBody appointmentDto: AppointmentDto): AppointmentDto {
        return appointmentService.addAppointment(appointmentDto);
    }

    @PostMapping("/csv")
    fun uploadCsvFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<List<AppointmentDto>> {
        logger.debug { "uploadCsvFile ${file.name}" }

        val appointmentDtos = csvService.uploadCsvFile(file, AppointmentDto::class.java)
        logger.debug { "parsed ${appointmentDtos.size} appointments successfully" }

        val savedAppointments = appointmentService.saveAppointments(appointmentDtos)
        logger.debug { "saved  ${savedAppointments.size} appointments successfully" }

        return ResponseEntity.ok(savedAppointments)
    }

}