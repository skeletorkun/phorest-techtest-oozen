package com.phorest.appointment.service.impl

import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.dto.PurchaseDto
import com.phorest.appointment.enums.PurchaseType
import com.phorest.appointment.repository.AppointmentRepository
import com.phorest.appointment.repository.ClientRepository
import com.phorest.appointment.repository.PurchaseRepository
import com.phorest.appointment.service.AppointmentService
import com.phorest.appointment.service.ClientService
import com.phorest.appointment.service.PurchaseService
import com.phorest.appointment.util.DateUtils.Companion.parseOffsetDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource

@SpringBootTest
internal class ClientServiceImplTest {

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var appointmentService: AppointmentService

    @Autowired
    lateinit var clientService: ClientService

    @Autowired
    lateinit var csvService: CsvServiceImpl

    @Autowired
    lateinit var clientRepository: ClientRepository

    @Autowired
    lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    lateinit var purchaseService: PurchaseService

    @BeforeEach
    internal fun setUp() {
        clientRepository.deleteAll()
        appointmentRepository.deleteAll()
        purchaseRepository.deleteAll()

        // import data from the CSV files
        val clientsFile = ClassPathResource("csv/clients.csv")
        val clients = csvService.uploadCsvFile(clientsFile.inputStream, ClientDto::class.java)
        clientService.saveClients(clients)

        val appointmentFile = ClassPathResource("csv/appointments.csv")
        val appointments = csvService.uploadCsvFile(appointmentFile.inputStream, AppointmentDto::class.java)
        appointmentService.saveAppointments(appointments)

        val purchaseFile = ClassPathResource("csv/purchases.csv")
        val purchases = csvService.uploadCsvFile(purchaseFile.inputStream, PurchaseDto::class.java)
        purchaseService.savePurchases(purchases, PurchaseType.PRODUCT)

        val servicesFile = ClassPathResource("csv/services.csv")
        val services = csvService.uploadCsvFile(servicesFile.inputStream, PurchaseDto::class.java)
        purchaseService.savePurchases(services, PurchaseType.SERVICE)

        assertEquals(100, clientRepository.count())
        assertEquals(490, appointmentRepository.count())
        assertEquals(1507, purchaseRepository.count())
    }

    @Test
    fun `When getTopClientsByLoyalty for size and a sinceDate then return top clients`() {

        var topClientsByLoyalty = clientService.getTopClientsByLoyalty(100, parseOffsetDateTime("2000-01-01 00:00:00 +0000"))
        assertEquals(90, topClientsByLoyalty.size)
        assertEquals("Christen", topClientsByLoyalty[0].firstName)
        assertEquals(965, topClientsByLoyalty[0].loyaltyPointsTotal)
        assertEquals("Florencia", topClientsByLoyalty.last().firstName)
        assertEquals(15, topClientsByLoyalty.last().loyaltyPointsTotal)

        topClientsByLoyalty = clientService.getTopClientsByLoyalty(100, parseOffsetDateTime("2018-01-01 00:00:00 +0000"))
        assertEquals(72, topClientsByLoyalty.size)
        assertEquals("Tinisha", topClientsByLoyalty[0].firstName)
        assertEquals(525, topClientsByLoyalty[0].loyaltyPointsTotal)
        assertEquals("Florencia", topClientsByLoyalty.last().firstName)
        assertEquals(15, topClientsByLoyalty.last().loyaltyPointsTotal)

        topClientsByLoyalty = clientService.getTopClientsByLoyalty(100, parseOffsetDateTime("2019-01-01 00:00:00 +0000"))
        assertEquals(7, topClientsByLoyalty.size)
        assertEquals("Gordon", topClientsByLoyalty[0].firstName)
        assertEquals(210, topClientsByLoyalty[0].loyaltyPointsTotal)
        assertEquals("Flora", topClientsByLoyalty.last().firstName)
        assertEquals(50, topClientsByLoyalty.last().loyaltyPointsTotal)

        assertEquals(90, clientService.getTopClientsByLoyalty(90, parseOffsetDateTime("2000-01-01 00:00:00 +0000")).size)
        assertEquals(10, clientService.getTopClientsByLoyalty(10, parseOffsetDateTime("2000-01-01 00:00:00 +0000")).size)
        assertEquals(7, clientService.getTopClientsByLoyalty(7, parseOffsetDateTime("2000-01-01 00:00:00 +0000")).size)
    }
}