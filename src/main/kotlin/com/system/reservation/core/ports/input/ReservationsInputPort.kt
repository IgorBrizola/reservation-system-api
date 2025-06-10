package com.system.reservation.core.ports.input

import com.system.reservation.core.domain.model.reservations.Reservations

interface ReservationsInputPort {
    fun save(reservations: Reservations)

    fun findAllReservation(): List<Reservations>

    fun verifyIfUserAlreadyReservation(userId: Int): Boolean

    fun findReservationById(reservationId: Int): Reservations

    fun cancelReservation(reservations: Reservations)
}
