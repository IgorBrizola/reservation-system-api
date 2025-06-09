package com.system.reservation.core.domain.model.reservations

import com.system.reservation.adapters.repository.model.StatusReservation
import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.domain.model.users.Users
import java.time.Instant

data class Reservations(
    val id: Int,
    val user: Users,
    val table: Tables,
    val dateReservation: Instant,
    val status: StatusReservation
)
