package com.system.reservation.core.ports.input

import com.system.reservation.core.domain.model.reservations.Reservations

interface ReservationsInputPort {
    fun save(reservations: Reservations)
}
