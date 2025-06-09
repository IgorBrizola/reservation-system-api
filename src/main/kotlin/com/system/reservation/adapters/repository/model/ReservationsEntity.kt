package com.system.reservation.adapters.repository.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
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
    @Column(name = "date_reservation")
    val dateReservation: Instant,
    @JsonProperty("id_status")
    val idStatus: Int,
)
