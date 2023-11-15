package com.phorest.appointment.controller

import com.phorest.appointment.domain.Appointment
import com.phorest.appointment.domain.Client
import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.repository.AppointmentRepository
import com.phorest.appointment.repository.ClientRepository
import com.phorest.appointment.util.DateUtils.Companion.parseOffsetDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class AppointmentControllerTest {

    @Autowired
    lateinit var clientRepository: ClientRepository

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var webTestClient: WebTestClient

    var savedClient: Client? = null;

    @BeforeEach
    internal fun setUp() {

        appointmentRepository.deleteAll()
        clientRepository.deleteAll()

        // create a client
        savedClient = clientRepository.save(
            Client(
                UUID.fromString("e0b8ebfc-6e57-4661-9546-328c644a3764"),
                "Dori",
                "Dietrich",
                "patrica@keeling.net",
                "(272) 301-6356",
                "Male",
            )
        )
    }

    @Test
    fun retrieveAppointment() {

        // create an appointment
        val savedAppointment = appointmentRepository.save(
            Appointment(
                UUID.randomUUID(),
                parseOffsetDateTime("2024-02-21 09:15:00 +0000"),
                parseOffsetDateTime("2024-02-21 09:15:00 +0000"),
                savedClient
            )
        )

        val appointmentDto = webTestClient.get()
            .uri("/v1/appointments/" + savedAppointment.id)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(AppointmentDto::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedAppointment.id, appointmentDto!!.id)
        assertEquals(savedAppointment.startTime, appointmentDto.startTime)
        assertEquals(savedAppointment.endTime, appointmentDto.endTime)
        assertEquals(savedAppointment.client!!.id, appointmentDto.clientId)
    }


    @Test
    fun addAppointment() {

        val clientId = savedClient!!.id
        val startTime = parseOffsetDateTime("2018-05-02 12:45:00 +0100")
        val endTime = parseOffsetDateTime("2018-05-02 13:45:00 +0100")
        val appointmentDto = AppointmentDto(UUID.randomUUID(), startTime, endTime, clientId)
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


    @Test
    fun csvImport() {

        val file = ClassPathResource("csv/appointments_small.csv").file

        val multipartBodyBuilder = MultipartBodyBuilder()
        multipartBodyBuilder.part("file", FileSystemResource(file)).filename(file.name)

        val appointmentDtos = webTestClient.post()
            .uri("/v1/appointments/csv")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(AppointmentDto::class.java)
            .returnResult()
            .responseBody

        assertEquals(3, appointmentDtos!!.count())

        assertEquals("67ce894a-9625-4ab7-8b91-17d83fb3fd10", appointmentDtos[0].id.toString())
        assertEquals("e0b8ebfc-6e57-4661-9546-328c644a3764", appointmentDtos[0].clientId.toString())

        assertTrue(parseOffsetDateTime("2017-05-09 14:30:00 +0000").isEqual(appointmentDtos[0].startTime))
    }
}

