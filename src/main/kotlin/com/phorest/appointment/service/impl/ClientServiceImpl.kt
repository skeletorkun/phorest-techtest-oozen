package com.phorest.appointment.service.impl

import com.phorest.appointment.domain.toDto
import com.phorest.appointment.dto.ClientDto
import com.phorest.appointment.dto.TopClientResponseDto
import com.phorest.appointment.dto.toEntity
import com.phorest.appointment.exception.BadRequestException
import com.phorest.appointment.exception.ResourceNotFoundException
import com.phorest.appointment.repository.ClientRepository
import com.phorest.appointment.service.ClientService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*
import kotlin.math.min


@Service
class ClientServiceImpl(
    val clientRepository: ClientRepository,
) : ClientService {

    private val logger = KotlinLogging.logger {}

    override fun saveClients(clientDtoList: List<ClientDto>): List<ClientDto> {
        val entities = clientDtoList.map {
            it.toEntity()
        }
        val savedClients = clientRepository.saveAll(entities)

        return savedClients.map {
            it.toDto()
        }
    }

    override fun getTopClientsByLoyalty(size: Int, sinceDate: OffsetDateTime): List<TopClientResponseDto> {
        if (size < 0) throw BadRequestException("Invalid size")
        val list = clientRepository.findTopClientsSortByLoyaltyPoints(sinceDate)
        return list.subList(0, min(list.size, size))
    }

    override fun retrieveClientById(id: UUID): ClientDto {
        logger.debug { "retrieveClientById $id" }
        val client = clientRepository.findById(id).orElseThrow { ResourceNotFoundException() }
        return client.toDto()
    }

    override fun addClient(clientDto: ClientDto): ClientDto {
        return clientRepository.save(clientDto.toEntity()).toDto()
    }

    override fun updateClient(clientDto: ClientDto): ClientDto {
        clientRepository.findById(clientDto.id).orElseThrow { ResourceNotFoundException() }
        return clientRepository.save(clientDto.toEntity()).toDto()
    }

    override fun deleteClient(clientId: UUID) {
        val client = clientRepository.findById(clientId).orElseThrow { ResourceNotFoundException() }
        clientRepository.delete(client)
    }
}
