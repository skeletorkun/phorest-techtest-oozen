package com.phorest.appointment.controller

import com.phorest.appointment.dto.PurchaseDto
import com.phorest.appointment.service.CsvService
import com.phorest.appointment.service.PurchaseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/purchases")
private class PurchaseController(val purchaseService: PurchaseService, val csvService: CsvService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/csv")
    fun uploadCsvFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<List<PurchaseDto>> {
        logger.debug { "uploadCsvFile ${file.name}" }

        val purchaseDtos = csvService.uploadCsvFile(file, PurchaseDto::class.java)
        logger.debug { "parsed ${purchaseDtos.size} purchases successfully" }

        val savedPurchases = purchaseService.savePurchases(purchaseDtos)
        logger.debug { "saved  ${savedPurchases.size} purchases successfully" }

        return ResponseEntity.ok(savedPurchases)
    }

}