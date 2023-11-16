package com.phorest.appointment.controller

import com.ninjasquad.springmockk.MockkBean
import com.phorest.appointment.domain.Client
import com.phorest.appointment.domain.toDto
import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.service.ClientService
import com.phorest.appointment.service.CsvService
import io.mockk.every
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


@WebMvcTest(ClientController::class)
internal class ClientControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var clientService: ClientService

    @MockkBean
    lateinit var csvService: CsvService

    @Test
    fun `When GET client by id then return object`() {

        val client = Client(UUID.randomUUID(), "john", "doe", "john@doe.com", "123456789", "male", false)
        every { clientService.retrieveClientById(client.id) } returns client.toDto()

        mockMvc.perform(get("/v1/clients/" + client.id.toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(client.id.toString()))
            .andExpect(jsonPath("\$.firstName").value(client.firstName))
    }


    @Disabled("Validation is missing - see README for explanation")
    @Test
    fun `When POST new client should return 2xx created`() {

        val clientDto = ClientDto(UUID.randomUUID(), "john", "doe", "john@doe.com", "123456789", "male", false)
        every { clientService.addClient(any()) } returns clientDto

        mockMvc.perform(
            post("/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "email": "john@doe.net",
                            "phone": "(272) 301-6356",
                            "gender": "Male",
                            "isBanned": false
                        }
                        """
                )
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(clientDto.id.toString()))
            .andExpect(jsonPath("\$.firstName").value(clientDto.firstName))
    }

    @Test
    fun `When POST new client with missing field then fail`() {

        mockMvc.perform(
            post("/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                    // missing first name
                    """
                        {
                            "lastName": "Doe",
                            "email": "john@doe.net",
                            "phone": "(272) 301-6356",
                            "gender": "Male",
                            "isBanned": false
                        }
                        """
                )
        )
            .andExpect(status().is4xxClientError)
    }


}

