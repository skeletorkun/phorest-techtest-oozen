package com.phorest.appointment.domain

import com.phorest.appointment.enums.PurchaseType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.*

@Entity
data class Purchase(

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    val id: UUID,
    val name: String? = null,
    val price: Double? = null,
    val loyaltyPoints: Int? = null,
    val type: PurchaseType? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    val appointment: Appointment? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Purchase

        if (id != other.id) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (loyaltyPoints != other.loyaltyPoints) return false
        if (appointment != other.appointment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        result = 31 * result + (loyaltyPoints ?: 0)
        result = 31 * result + (appointment?.hashCode() ?: 0)
        return result
    }

}