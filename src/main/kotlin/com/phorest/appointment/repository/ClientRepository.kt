package com.phorest.appointment.repository

import com.phorest.appointment.domain.Client
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ClientRepository : CrudRepository<Client, UUID>