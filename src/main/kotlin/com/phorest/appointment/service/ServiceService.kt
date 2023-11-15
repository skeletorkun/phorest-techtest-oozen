package com.phorest.appointment.service

import com.phorest.appointment.dto.ServiceDto

fun interface ServiceService {
    fun saveServices(serviceDtoList: List<ServiceDto>): List<ServiceDto>
}