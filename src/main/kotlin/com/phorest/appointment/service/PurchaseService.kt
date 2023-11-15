package com.phorest.appointment.service

import com.phorest.appointment.dto.PurchaseDto
import com.phorest.appointment.enums.PurchaseType

fun interface PurchaseService {
    fun savePurchases(purchaseDtoList: List<PurchaseDto>, type: PurchaseType): List<PurchaseDto>
}