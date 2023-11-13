package com.phorest.appointment.service

import com.phorest.appointment.dto.ClientDto

fun interface ClientService {
    fun saveClients(clientDtoList: List<ClientDto>): List<ClientDto>
}
