package com.phorest.appointment.dto

import com.opencsv.bean.CsvBindByName
import com.phorest.appointment.domain.Client
import java.util.*


data class ClientDto(

    @CsvBindByName(column = "id")
    val id: UUID = UUID.randomUUID(),

    @CsvBindByName(column = "first_name")
    val firstName: String = "",

    @CsvBindByName(column = "last_name")
    val lastName: String = "",

    @CsvBindByName(column = "email")
    val email: String = "",

    @CsvBindByName(column = "phone")
    val phone: String = "",

    @CsvBindByName(column = "gender")
    val gender: String = "",

    @CsvBindByName(column = "banned")
    val isBanned: Boolean = false,
)

fun ClientDto.toEntity() = Client(
    this.id,
    this.firstName,
    this.lastName,
    this.email,
    this.phone,
    this.gender,
    this.isBanned
)