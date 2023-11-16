package com.phorest.appointment.domain

import com.phorest.appointment.dto.ClientDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.Hibernate
import java.util.*

@Entity
data class Client(

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val gender: String,
    val isBanned: Boolean = false,

    @OneToMany(
        mappedBy = "client",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val appointments: List<Appointment> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Client

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstName = $firstName , lastName = $lastName , email = $email , phone = $phone , gender = $gender , isBanned = $isBanned )"
    }
}

fun Client.toDto() = ClientDto(
    this.id,
    this.firstName,
    this.lastName,
    this.email,
    this.phone,
    this.gender,
    this.isBanned
)

