package com.phorest.appointment.dto

import java.util.*

data class ClientDto(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val gender: String,
    val idBanned: Boolean = false,
)