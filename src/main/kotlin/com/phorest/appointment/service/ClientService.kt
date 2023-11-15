package com.phorest.appointment.service

import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.dto.TopClientResponseDto
import java.time.OffsetDateTime

interface ClientService {
    fun saveClients(clientDtoList: List<ClientDto>): List<ClientDto>
    fun getTopClientsByLoyalty(size: Int, sinceDate: OffsetDateTime): List<TopClientResponseDto>
}
