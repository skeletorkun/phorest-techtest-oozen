package com.phorest.appointment.domain

import com.opencsv.bean.CsvBindByName
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.Hibernate
import java.util.*

@Entity
data class Client(

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    @CsvBindByName(column = "id")
    val id: UUID?,

    @CsvBindByName(column = "first_name")
    val firstName: String,

    @CsvBindByName(column = "last_name")
    val lastName: String,

    @CsvBindByName(column = "email")
    val email: String,

    @CsvBindByName(column = "phone")
    val phone: String,

    @CsvBindByName(column = "gender")
    val gender: String,

    @CsvBindByName(column = "banned")
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

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstName = $firstName , lastName = $lastName , email = $email , phone = $phone , gender = $gender , isBanned = $isBanned )"
    }
}