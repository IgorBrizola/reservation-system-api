package com.system.reservation.core.ports.output

import com.system.reservation.adapters.web.model.response.ReservationsResponse
import com.system.reservation.core.domain.model.reservations.Reservations

interface ReservationsOutPutPort {
    fun save(reservations: Reservations)

    fun findAllReservations(): List<ReservationsResponse>

    fun existsReservationByUserId(userId: Int): Boolean
}
