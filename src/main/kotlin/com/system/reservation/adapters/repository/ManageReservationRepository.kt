package com.system.reservation.adapters.repository

import com.system.reservation.adapters.repository.jpa.ReservationsJpaRepository
import com.system.reservation.core.ports.output.ReservationsOutPutPort
import org.springframework.stereotype.Repository

@Repository
class ManageReservationRepository(
    private val reservationsJpaRepository: ReservationsJpaRepository
): ReservationsOutPutPort{
}