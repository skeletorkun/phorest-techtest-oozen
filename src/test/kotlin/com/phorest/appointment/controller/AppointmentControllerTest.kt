package com.phorest.appointment.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class AppointmentControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun retrieveAppointment() {

        val result = webTestClient.get()
            .uri("/v1/appointments/123")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("{\"id\":\"123\",\"text\":\"Something\"}", result.responseBody)
    }
}