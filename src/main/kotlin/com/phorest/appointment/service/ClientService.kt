package com.phorest.appointment.service

import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.dto.TopClientResponseDto
import java.time.OffsetDateTime
import java.util.*

interface ClientService {
    fun saveClients(clientDtoList: List<ClientDto>): List<ClientDto>
    fun getTopClientsByLoyalty(size: Int, sinceDate: OffsetDateTime): List<TopClientResponseDto>
    fun retrieveClientById(id: UUID): ClientDto
    fun addClient(clientDto: ClientDto): ClientDto
    fun updateClient(clientDto: ClientDto): ClientDto
    fun deleteClient(clientId: UUID)
}
