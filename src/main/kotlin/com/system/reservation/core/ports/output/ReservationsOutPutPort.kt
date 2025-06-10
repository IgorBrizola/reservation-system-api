package com.system.reservation.core.ports.output

import com.system.reservation.core.domain.model.reservations.Reservations

interface ReservationsOutPutPort {
    fun save(reservations: Reservations)

    fun updateReservation(reservations: Reservations)

    fun findAllReservations(): List<Reservations>

    fun existsReservationByUserId(userId: Int): Boolean

    fun findReservationById(reservationId: Int): Reservations
}
