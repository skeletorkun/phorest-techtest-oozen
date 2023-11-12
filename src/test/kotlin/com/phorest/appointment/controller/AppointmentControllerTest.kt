package com.phorest.appointment.controller

import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.util.DateUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class AppointmentControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun retrieveAppointment() {

        val appointmentDto = webTestClient.get()
            .uri("/v1/appointments/123")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(AppointmentDto::class.java)
            .returnResult()
            .responseBody

        assertTrue(appointmentDto!!.id != null)
    }


    @Test
    fun addAppointment() {

        val clientId = UUID.randomUUID()
        val startTime = DateUtils.parseOffsetDateTime("2018-05-02 12:45:00 +0100")
        val endTime = DateUtils.parseOffsetDateTime("2018-05-02 13:45:00 +0100")
        val appointmentDto = AppointmentDto(null, clientId, startTime, endTime)
        val appointmentDtoResult = webTestClient.post()
            .uri("/v1/appointments")
            .bodyValue(appointmentDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(AppointmentDto::class.java)
            .returnResult()
            .responseBody

        assertNotNull(appointmentDtoResult!!.id)
        assertEquals(clientId, appointmentDtoResult.clientId)
        assertTrue(startTime.isEqual(appointmentDtoResult.startTime))
        assertTrue(endTime.isEqual(appointmentDtoResult.endTime))
    }
}