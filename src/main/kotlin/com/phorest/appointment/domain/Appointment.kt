package com.phorest.appointment.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.phorest.appointment.dto.AppointmentDto
import com.phorest.appointment.util.Constants
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.Hibernate
import java.time.OffsetDateTime
import java.util.*

@Entity
data class Appointment(

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    val id: UUID,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.PHOREST_OFFSET_DATE_TIME_FORMAT)
    val startTime: OffsetDateTime? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.PHOREST_OFFSET_DATE_TIME_FORMAT)
    val endTime: OffsetDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    val client: Client? = null,

    @OneToMany(
        mappedBy = "appointment",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val purchases: List<Purchase> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Appointment

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , clientId = $client?.id , startTime = $startTime , endTime = $endTime )"
    }
}

fun Appointment.toDto() = AppointmentDto(
    this.id,
    this.startTime,
    this.endTime,
    this.client!!.id
)