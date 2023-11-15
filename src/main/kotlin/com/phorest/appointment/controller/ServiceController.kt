package com.phorest.appointment.controller

import com.phorest.appointment.dto.ServiceDto
import com.phorest.appointment.service.CsvService
import com.phorest.appointment.service.ServiceService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/services")
private class ServiceController(val serviceService: ServiceService, val csvService: CsvService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/csv")
    fun uploadCsvFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<List<ServiceDto>> {
        logger.debug { "uploadCsvFile ${file.name}" }

        val serviceDtos = csvService.uploadCsvFile(file, ServiceDto::class.java)
        logger.debug { "parsed ${serviceDtos.size} services successfully" }

        val savedServices = serviceService.saveServices(serviceDtos)
        logger.debug { "saved  ${savedServices.size} services successfully" }

        return ResponseEntity.ok(savedServices)
    }

}