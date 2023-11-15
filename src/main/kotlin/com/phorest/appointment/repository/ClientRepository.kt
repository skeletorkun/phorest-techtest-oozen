package com.phorest.appointment.repository

import com.phorest.appointment.domain.Client
import com.phorest.appointment.dto.TopClientResponseDto
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.OffsetDateTime
import java.util.*

interface ClientRepository : CrudRepository<Client, UUID> {
    @Query(
        "select new com.phorest.appointment.dto.TopClientResponseDto(" +
                "c.id, " +
                "c.firstName, " +
                "c.lastName, " +
                "c.email, " +
                "c.phone, " +
                "c.gender, " +
                "sum(p.loyaltyPoints)) " +
                "from Client as c " +
                "left join Appointment as a on a.client.id = c.id " +
                "left join Purchase as p on p.appointment.id = a.id " +
                "where c.isBanned = false " +
                "and a.startTime > :sinceDate " +
                "group by c.id " +
                "order by sum(p.loyaltyPoints) desc "
    )
    fun findTopClientsSortByLoyaltyPoints(@Param("sinceDate") sinceDate: OffsetDateTime): List<TopClientResponseDto>
}