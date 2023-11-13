package com.phorest.appointment.controller

import com.phorest.appointment.domain.Client
import com.phorest.appointment.service.CsvService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/clients")
private class ClientController(val csvService: CsvService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/csv")
    fun uploadCsvFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<List<Client>> {
        logger.debug { "uploadCsvFile ${file.name}" }

        val importedEntries = csvService.uploadCsvFile(file, Client::class.java)
        logger.debug { "imported  ${importedEntries.size} clients successfully" }

        return ResponseEntity.ok(importedEntries)
    }

}