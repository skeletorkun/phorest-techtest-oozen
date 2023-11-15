package com.phorest.appointment.repository

import com.phorest.appointment.domain.Purchase
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PurchaseRepository : CrudRepository<Purchase, UUID>