package com.system.reservation.adpters.repository.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "reservations")
data class ReservationsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @OneToOne
    @JoinColumn(name = "user_id")
    val user: UsersEntity,
    @OneToOne
    @JoinColumn(name = "table_id")
    val table: TablesEntity,
    val dateReservation: Instant,
    val status: StatusReservation
)

enum class StatusReservation { ACTIVE, CANCELED}