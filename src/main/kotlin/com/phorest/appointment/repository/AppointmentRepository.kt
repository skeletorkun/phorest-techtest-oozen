package com.phorest.appointment.repository

import com.phorest.appointment.domain.Appointment
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AppointmentRepository : CrudRepository<Appointment, UUID>