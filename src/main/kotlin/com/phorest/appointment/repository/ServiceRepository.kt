package com.phorest.appointment.repository

import com.phorest.appointment.domain.Service
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ServiceRepository : CrudRepository<Service, UUID>