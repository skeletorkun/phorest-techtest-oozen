package com.phorest.appointment.service

import com.phorest.appointment.dto.PurchaseDto

fun interface PurchaseService {
    fun savePurchases(purchaseDtoList: List<PurchaseDto>): List<PurchaseDto>
}