package com.system.reservation.core.ports.input

import com.system.reservation.adapters.web.model.response.ReservationsResponse
import com.system.reservation.core.domain.model.reservations.Reservations

interface ReservationsInputPort {
    fun save(reservations: Reservations)

    fun findAllReservation(): List<ReservationsResponse>

    fun verifyIfUserAlreadyReservation(userId: Int): Boolean
}
