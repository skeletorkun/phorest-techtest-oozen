package com.phorest.appointment.controller

import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.dto.TopClientResponseDto
import com.phorest.appointment.service.ClientService
import com.phorest.appointment.service.CsvService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

@RestController
@RequestMapping("/v1/clients")
class ClientController(
    val csvService: CsvService,
    val clientService: ClientService
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addClient(@RequestBody @Validated clientDto: ClientDto): ClientDto {
        return clientService.addClient(clientDto);
    }

    @GetMapping("/{id}")
    fun retrieveClient(@PathVariable("id") id: UUID): ClientDto {
        return clientService.retrieveClientById(id);
    }

    @PatchMapping("/{id}")
    fun updateClient(@RequestBody @Validated clientDto: ClientDto): ClientDto {
        return clientService.updateClient(clientDto);
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: UUID) {
        return clientService.deleteClient(id);
    }

    @GetMapping("/top")
    fun topClientsByLoyalty(
        @RequestParam("size") size: Int,
        @RequestParam("sinceDate") @DateTimeFormat(pattern = "yyyy-MM-dd") sinceDate: Date,
    ): ResponseEntity<List<TopClientResponseDto>> {
        logger.debug { "topClientsByLoyalty size:${size} sinceDate:${sinceDate}" }

        val offsetDateTime: OffsetDateTime = sinceDate.toInstant().atOffset(ZoneOffset.UTC)
        val clients = clientService.getTopClientsByLoyalty(size, offsetDateTime)
        logger.debug { "calculated ${clients.size} clients successfully" }

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