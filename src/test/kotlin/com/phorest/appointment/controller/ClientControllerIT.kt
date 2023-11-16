package com.phorest.appointment.controller

import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.repository.ClientRepository
import org.junit.jupiter.api.Assertions.assertEquals
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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class ClientControllerIT {

    @Autowired
    lateinit var clientRepository: ClientRepository


    @Autowired
    lateinit var webTestClient: WebTestClient

    @BeforeEach
    internal fun setUp() {
        clientRepository.deleteAll()
    }

    @Test
    fun `When POST a CSV file is received then store and return objects`() {

        // prepare
        val file = ClassPathResource("csv/clients_small.csv").file

        val multipartBodyBuilder = MultipartBodyBuilder()
        multipartBodyBuilder.part("file", FileSystemResource(file)).filename(file.name)

        // action
        val clientDtos = webTestClient.post()
            .uri("/v1/clients/csv")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(ClientDto::class.java)
            .returnResult()
            .responseBody

        // assert the api response
        val first = clientDtos?.get(0)
        val second = clientDtos?.get(1)

        assertEquals("e0b8ebfc-6e57-4661-9546-328c644a3764", first!!.id.toString())
        assertEquals("Dori", first.firstName)

        assertEquals("104fdf33-c8a2-4f1c-b371-3e9c2facdfa0", second!!.id.toString())
        assertEquals("Gordon", second.firstName)


        // assert the clients are in db
        val dori = clientRepository.findById(first.id)
        assertTrue(dori.isPresent)
        assertEquals(first.id, dori.get().id)
        assertEquals(first.email, dori.get().email)
        assertEquals(first.isBanned, dori.get().isBanned)

        val gordon = clientRepository.findById(second.id);
        assertTrue(gordon.isPresent)
        assertEquals(second.id, gordon.get().id)
        assertEquals(second.email, gordon.get().email)
        assertEquals(second.isBanned, gordon.get().isBanned)
    }


    @Test
    fun `When POST a bad CSV file is received then do not store any objects and return empty`() {

        // prepare
        val file = ClassPathResource("csv/bad_file.csv").file

        val multipartBodyBuilder = MultipartBodyBuilder()
        multipartBodyBuilder.part("file", FileSystemResource(file)).filename(file.name)

        // action
        val clientDtos = webTestClient.post()
            .uri("/v1/clients/csv")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(ClientDto::class.java)
            .returnResult()
            .responseBody

        // assert return body is empty
        assertEquals(0, clientDtos!!.count())

        // assert db is empty
        assertEquals(0, clientRepository.count())
    }


    @Test
    fun `When POST an empty CSV file is received then do not store any objects and return 4xx`() {

        // prepare
        val file = ClassPathResource("csv/empty.csv").file

        val multipartBodyBuilder = MultipartBodyBuilder()
        multipartBodyBuilder.part("file", FileSystemResource(file)).filename(file.name)

        // action
        webTestClient.post()
            .uri("/v1/clients/csv")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
            .exchange()
            .expectStatus().is4xxClientError

        // assert db is empty
        assertEquals(0, clientRepository.count())
    }
}

