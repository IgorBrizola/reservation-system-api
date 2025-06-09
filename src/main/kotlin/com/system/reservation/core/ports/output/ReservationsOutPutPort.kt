package com.system.reservation.core.ports.output

import com.system.reservation.core.domain.model.reservations.Reservations

interface ReservationsOutPutPort {
    fun save(reservations: Reservations)
}
