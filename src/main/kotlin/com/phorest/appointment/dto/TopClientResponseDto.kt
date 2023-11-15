package com.phorest.appointment.dto

import java.util.*


data class TopClientResponseDto(
    val id: UUID = UUID.randomUUID(),
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val gender: String? = "",
    val loyaltyPointsTotal: Long? = 0,
)