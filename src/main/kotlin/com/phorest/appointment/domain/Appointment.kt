package com.phorest.appointment.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.phorest.appointment.util.Constants
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.Hibernate
import java.time.OffsetDateTime
import java.util.*

@Entity
data class Appointment(

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    val id: UUID?,
    val clientId: UUID,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.PHOREST_OFFSET_DATE_TIME_FORMAT)
    val startTime: OffsetDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.PHOREST_OFFSET_DATE_TIME_FORMAT)
    val endTime: OffsetDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Appointment

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , clientId = $clientId , startTime = $startTime , endTime = $endTime )"
    }
}