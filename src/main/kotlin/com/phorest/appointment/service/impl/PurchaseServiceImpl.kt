package com.phorest.appointment.service.impl

import com.phorest.appointment.domain.Purchase
import com.phorest.appointment.dto.PurchaseDto
import com.phorest.appointment.repository.AppointmentRepository
import com.phorest.appointment.repository.PurchaseRepository
import com.phorest.appointment.service.PurchaseService
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    val purchaseRepository: PurchaseRepository,
    val appointmentRepository: AppointmentRepository,
) : PurchaseService {
    override fun savePurchases(purchaseDtoList: List<PurchaseDto>): List<PurchaseDto> {

        val entities = purchaseDtoList.map {
            Purchase(
                it.id,
                it.name,
                it.price,
                it.loyaltyPoints,
                it.appointmentId?.let { id -> appointmentRepository.findById(id).orElseThrow() }
            )
        }

        val savedEntities = purchaseRepository.saveAll(entities)

        return savedEntities.map {
            PurchaseDto(
                it.id,
                it.name,
                it.price,
                it.loyaltyPoints,
                it.appointment?.id
            )
        }
    }
}