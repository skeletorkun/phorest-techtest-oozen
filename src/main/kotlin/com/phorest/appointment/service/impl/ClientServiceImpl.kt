package com.phorest.appointment.service.impl

import com.phorest.appointment.domain.Client
import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.dto.TopClientResponseDto
import com.phorest.appointment.exception.BadRequestException
import com.phorest.appointment.repository.ClientRepository
import com.phorest.appointment.service.ClientService
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import kotlin.math.min


@Service
class ClientServiceImpl(
    val clientRepository: ClientRepository,
) : ClientService {
    override fun saveClients(clientDtoList: List<ClientDto>): List<ClientDto> {
        val entities = clientDtoList.map {
            Client(
                it.id,
                it.firstName,
                it.lastName,
                it.email,
                it.phone,
                it.gender,
                it.isBanned
            )
        }
        val savedClients = clientRepository.saveAll(entities)

        return savedClients.map {
            ClientDto(
                it.id,
                it.firstName,
                it.lastName,
                it.email,
                it.phone,
                it.gender,
                it.isBanned
            )
        }
    }

    override fun getTopClientsByLoyalty(size: Int, sinceDate: OffsetDateTime): List<TopClientResponseDto> {
        if (size < 0) throw BadRequestException("Invalid size")
        val list = clientRepository.findTopClientsSortByLoyaltyPoints(sinceDate)
        return list.subList(0, min(list.size, size))
    }
}
