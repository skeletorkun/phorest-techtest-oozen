package com.phorest.appointment.controller

import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.service.ClientService
import com.phorest.appointment.service.CsvService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/clients")
private class ClientController(
    val csvService: CsvService,
    val clientService: ClientService
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/top")
    fun topClientsByLoyalty(
        @RequestParam("size") size: Int,
        @RequestParam("sinceDate") @DateTimeFormat(pattern = "yyyy-MM-dd") sinceDate: Date,
    ): ResponseEntity<List<ClientDto>> {
        logger.debug { "topClientsByLoyalty size:${size} sinceDate:${sinceDate}" }

        val clients = clientService.getTopClientsByLoyalty(size, sinceDate)
        logger.debug { "calculated ${clients?.size} clients successfully" }

        return ResponseEntity.ok(clients)
    }

    @PostMapping("/csv")
    fun uploadCsvFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<List<ClientDto>> {
        logger.debug { "uploadCsvFile ${file.name}" }

        val clients = csvService.uploadCsvFile(file, ClientDto::class.java)
        logger.debug { "parsed ${clients.size} clients successfully" }

        val savedClients = clientService.saveClients(clients)
        logger.debug { "saved  ${savedClients.size} clients successfully" }

        return ResponseEntity.ok(savedClients)
    }

}